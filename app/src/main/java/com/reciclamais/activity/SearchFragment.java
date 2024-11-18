package com.reciclamais.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.reciclamais.R;
import com.reciclamais.adapter.ProdutoAdapter;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView recyclerProdutos;
    private ProdutoAdapter adapter; // Suponha que você tenha um adapter para a RecyclerView
    private DatabaseReference databaseReference;
    private List<Produto> produtoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inicializa a referência ao Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("produtos");

        // Inicializa a RecyclerView e o Adapter
        recyclerProdutos = view.findViewById(R.id.recyclerProdutos);
        recyclerProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        produtoList = new ArrayList<>();
        adapter = new ProdutoAdapter(produtoList, getContext());
        recyclerProdutos.setAdapter(adapter);

        // Configurar click do botão de ordenação
        view.findViewById(R.id.button3).setOnClickListener(v -> ordenaDificuldade());
        view.findViewById(R.id.button4).setOnClickListener(v -> ordenaAvaliacoes());


        // Inicializa o SearchView e define o listener
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pesquisarProdutos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisarProdutos(newText);
                return true;
            }
        });

        return view;
    }

    private void pesquisarProdutos(String texto) {
        Query query = databaseReference.orderByChild("nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtoList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Produto produto = snapshot.getValue(Produto.class);
                    if (produto != null) {
                        produtoList.add(produto);
                    }
                }
                adapter.atualizarLista(produtoList);
                adapter.filtrarPorNome(texto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Erro na consulta: " + databaseError.getMessage());
            }
        });
    }

    //Metodos de ordenacao
    public void ordenaDificuldade(){
        adapter.ordenaPorDificul();
    }
    public void ordenaAvaliacoes(){ adapter.ordenaPorMediaAvaliacoes();}

}
