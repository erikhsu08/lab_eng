package com.reciclamais.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.reciclamais.R;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class PublishFragment extends Fragment {

    private EditText editTextNome, editTextPassos, editTextMateriais, editTextTags, editTextImagem;
    private LinearLayout containerPassos, containerMateriais, containerTags;
    private Button buttonAdicionarPasso, buttonAdicionarMaterial, buttonAdicionarTag, buttonPublicar;
    private Spinner spinnerNivelDificuldade;

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    private List<String> passos = new ArrayList<>();
    private List<String> materiais = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        // Inicialize as Views
        editTextNome = view.findViewById(R.id.editTextNome);
        editTextPassos = view.findViewById(R.id.editTextPassos);
        editTextMateriais = view.findViewById(R.id.editTextMateriais);
        editTextTags = view.findViewById(R.id.editTextTags);
        editTextImagem = view.findViewById(R.id.editTextImagem);
        containerPassos = view.findViewById(R.id.containerPassos);
        containerMateriais = view.findViewById(R.id.containerMateriais);
        containerTags = view.findViewById(R.id.containerTags);
        buttonAdicionarPasso = view.findViewById(R.id.buttonAdicionarPasso);
        buttonAdicionarMaterial = view.findViewById(R.id.buttonAdicionarMaterial);
        buttonAdicionarTag = view.findViewById(R.id.buttonAdicionarTag);
        buttonPublicar = view.findViewById(R.id.buttonPublicar);
        spinnerNivelDificuldade = view.findViewById(R.id.spinnerNivelDificuldade);

        // Inicialize o Firebase
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("produtos");
        storageRef = FirebaseStorage.getInstance().getReference("produtos");

        // Configura o botão para adicionar passos
        buttonAdicionarPasso.setOnClickListener(v -> adicionarPasso());

        // Configura o botão para adicionar materiais
        buttonAdicionarMaterial.setOnClickListener(v -> adicionarMaterial());

        // Configura o botão para adicionar tags
        buttonAdicionarTag.setOnClickListener(v -> adicionarTag());

        // Configura o botão de publicar
        buttonPublicar.setOnClickListener(v -> salvarProduto());

        // Configura o Spinner com o ArrayAdapter
        configuraSpinnerNivelDificuldade();

        return view;
    }

    private void adicionarPasso() {
        String passoTexto = editTextPassos.getText().toString();
        if (!passoTexto.isEmpty()) {
            passos.add(passoTexto);
            exibirPasso(passoTexto);
            editTextPassos.setText("");
        }
    }

    private void adicionarMaterial() {
        String materialTexto = editTextMateriais.getText().toString();
        if (!materialTexto.isEmpty()) {
            materiais.add(materialTexto);
            exibirMaterial(materialTexto);
            editTextMateriais.setText("");
        }
    }

    private void adicionarTag() {
        String tagTexto = editTextTags.getText().toString();
        if (!tagTexto.isEmpty()) {
            tags.add(tagTexto.trim());
            exibirTag(tagTexto.trim());
            editTextTags.setText("");
        }
    }

    private void exibirPasso(String passo) {
        TextView textViewPasso = new TextView(getContext());
        textViewPasso.setText("• " + passo);
        containerPassos.addView(textViewPasso);
    }

    private void exibirMaterial(String material) {
        TextView textViewMaterial = new TextView(getContext());
        textViewMaterial.setText("• " + material);
        containerMateriais.addView(textViewMaterial);
    }

    private void exibirTag(String tag) {
        TextView textViewTag = new TextView(getContext());
        textViewTag.setText("• " + tag);
        containerTags.addView(textViewTag);
    }

    private void salvarProduto() {
        String nome = editTextNome.getText().toString();
        String nivel = spinnerNivelDificuldade.getSelectedItem().toString();
        String imagem = editTextImagem.getText().toString();

        // Formata o nome para usar como chave
        String produtoId = formatarNomeParaChave(nome);

        // Cria o objeto Produto
        Produto produto = new Produto(nome, nivel, null, imagem, passos, materiais, tags);

        // Salva o produto no Firebase usando o nome formatado como chave
        databaseRef.child(produtoId).setValue(produto)
                .addOnSuccessListener(aVoid -> {
                    salvarImagem(produtoId);
                    limparCampos();
                    Toast.makeText(getContext(), "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao cadastrar o produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Função para formatar o nome
    private String formatarNomeParaChave(String nome) {
        return nome.toLowerCase().replace(" ", "_").replaceAll("[^a-zA-Z0-9_]", "");
    }

    private void salvarImagem(String produtoId) {
        // Pega o valor da URL da imagem diretamente do campo editTextImagem
        String urlImagem = editTextImagem.getText().toString();

        // Salva a URL diretamente no Firebase Realtime Database
        databaseRef.child(produtoId).child("imagem").setValue(urlImagem)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao salvar a imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void limparCampos() {
        editTextNome.setText("");
        containerPassos.removeAllViews();
        containerMateriais.removeAllViews();
        editTextTags.setText("");
        editTextImagem.setText("");
        passos.clear();
        materiais.clear();
        tags.clear();
    }

    private void configuraSpinnerNivelDificuldade() {
        // Configura o ArrayAdapter com o layout personalizado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.nivel_dificuldade, // Array de strings definido em res/values/strings.xml
                R.layout.spinner_item // Layout do item personalizado
        );

        adapter.setDropDownViewResource(R.layout.spinner_item); // Layout para o dropdown também
        spinnerNivelDificuldade.setAdapter(adapter);
    }
}