package com.reciclamais.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reciclamais.R;

import java.util.ArrayList;
import java.util.List;

public class PublishFragment extends Fragment {

    private LinearLayout containerPassos;
    private LinearLayout containerMateriais;
    private Button buttonAdicionarPasso;
    private Button buttonAdicionarMaterial;

    private List<String> listaPassos = new ArrayList<>();
    private List<String> listaMateriais = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        return inflater.inflate(R.layout.fragment_publish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa as Views após o layout ser inflado
        containerPassos = view.findViewById(R.id.containerPassos);
        containerMateriais = view.findViewById(R.id.containerMateriais);
        buttonAdicionarPasso = view.findViewById(R.id.buttonAdicionarPasso);
        buttonAdicionarMaterial = view.findViewById(R.id.buttonAdicionarMaterial);

        // Configura os listeners dos botões
        buttonAdicionarPasso.setOnClickListener(v -> adicionarPasso());
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
    }

    private void adicionarPasso() {
        // Aqui você pode usar um AlertDialog para permitir que o usuário digite o passo
        String novoPasso = "Passo exemplo"; // Substitua com o valor obtido no diálogo
        listaPassos.add(novoPasso);
        atualizarListaPassos();
    }

    private void adicionarMaterial() {
        // Aqui você pode usar um AlertDialog para permitir que o usuário digite o material
        String novoMaterial = "Material exemplo"; // Substitua com o valor obtido no diálogo
        listaMateriais.add(novoMaterial);
        atualizarListaMateriais();
    }

    private void atualizarListaPassos() {
        containerPassos.removeAllViews();
        for (String passo : listaPassos) {
            TextView textView = new TextView(getContext());
            textView.setText(passo);
            textView.setPadding(16, 16, 16, 16);
            textView.setTextColor(getResources().getColor(R.color.black));
            containerPassos.addView(textView);
        }
    }

    private void atualizarListaMateriais() {
        containerMateriais.removeAllViews();
        for (String material : listaMateriais) {
            TextView textView = new TextView(getContext());
            textView.setText(material);
            textView.setPadding(16, 16, 16, 16);
            textView.setTextColor(getResources().getColor(R.color.black));
            containerMateriais.addView(textView);
        }
    }
}
