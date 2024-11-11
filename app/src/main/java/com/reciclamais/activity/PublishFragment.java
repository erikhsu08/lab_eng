package com.reciclamais.activity;

import android.graphics.Color;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.reciclamais.R;

public class PublishFragment extends Fragment {

    private EditText editTextPassos, editTextMateriais;
    private LinearLayout containerPassos, containerMateriais;
    private Button buttonAdicionarPasso, buttonAdicionarMaterial;
    private int passoNumero = 1; // contador para numerar os passos

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        // Inicialize as Views
        editTextPassos = view.findViewById(R.id.editTextPassos);
        editTextMateriais = view.findViewById(R.id.editTextMateriais);
        containerPassos = view.findViewById(R.id.containerPassos);
        containerMateriais = view.findViewById(R.id.containerMateriais);
        buttonAdicionarPasso = view.findViewById(R.id.buttonAdicionarPasso);
        buttonAdicionarMaterial = view.findViewById(R.id.buttonAdicionarMaterial);

        // Configura o botão para adicionar passos
        buttonAdicionarPasso.setOnClickListener(v -> adicionarPasso());

        // Configura o botão para adicionar materiais
        buttonAdicionarMaterial.setOnClickListener(v -> adicionarMaterial());

        // Configurando o Spinner com o ArrayAdapter
        Spinner spinnerNivelDificuldade = view.findViewById(R.id.spinnerNivelDificuldade);

        // Configura o ArrayAdapter com o layout personalizado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.nivel_dificuldade, // Array de strings definido em res/values/strings.xml
                R.layout.spinner_item // Layout do item personalizado
        );

        adapter.setDropDownViewResource(R.layout.spinner_item); // Layout para o dropdown também
        spinnerNivelDificuldade.setAdapter(adapter);

        return view;
    }

    // Método para adicionar um novo passo numerado
    private void adicionarPasso() {
        String passoTexto = editTextPassos.getText().toString();
        if (!passoTexto.isEmpty()) {
            TextView textViewPasso = new TextView(getContext());
            textViewPasso.setText(passoNumero + ". " + passoTexto); // adiciona número do passo
            textViewPasso.setTextColor(Color.BLACK); // define cor do texto como preto
            containerPassos.addView(textViewPasso);
            editTextPassos.setText("");
            passoNumero++; // incrementa o número do passo para o próximo
        }
    }

    // Método para adicionar um novo material com bullet point
    private void adicionarMaterial() {
        String materialTexto = editTextMateriais.getText().toString();
        if (!materialTexto.isEmpty()) {
            TextView textViewMaterial = new TextView(getContext());
            textViewMaterial.setText("• " + materialTexto); // adiciona bullet point
            textViewMaterial.setTextColor(Color.BLACK); // define cor do texto como preto
            containerMateriais.addView(textViewMaterial);
            editTextMateriais.setText("");
        }
    }
}
