package com.reciclamais.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reciclamais.R;
import com.reciclamais.adapter.ProdutoAdapter;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private List<Produto> produtosFavoritos;
    private DatabaseReference produtosRef;
    private Button btnRecente, btnDificuldade;
    private boolean ordenadoPorDificuldade = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        // Inicializa componentes
        recyclerView = view.findViewById(R.id.recyclerProdutos);
        btnRecente = view.findViewById(R.id.button2);
        btnDificuldade = view.findViewById(R.id.button3);

        // Configura RecyclerView
        produtosFavoritos = new ArrayList<>();
        adapter = new ProdutoAdapter(produtosFavoritos, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        // Configura referência do Firebase
        produtosRef = FirebaseDatabase.getInstance().getReference().child("produtos");

        // Carrega produtos favoritos
        carregarProdutosFavoritos();

        // Configura listeners dos botões de ordenação
        btnRecente.setOnClickListener(v -> {
            ordenadoPorDificuldade = false;
            adapter.atualizarLista(produtosFavoritos);
        });

        btnDificuldade.setOnClickListener(v -> {
            ordenadoPorDificuldade = true;
            adapter.ordenaPorDificul();
        });

        return view;
    }

    private void carregarProdutosFavoritos() {
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtosFavoritos.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Produto produto = ds.getValue(Produto.class);
                    if (produto != null && produto.isFavoritado()) {
                        produto.setKey(ds.getKey());
                        produtosFavoritos.add(produto);
                    }
                }

                if (ordenadoPorDificuldade) {
                    adapter.ordenaPorDificul();
                } else {
                    adapter.atualizarLista(produtosFavoritos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tratar erro se necessário
            }
        });
    }
}