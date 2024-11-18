package com.reciclamais.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();
    private ProdutoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerProdutos = view.findViewById(R.id.recyclerProdutos);

        // Definir layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerProdutos.setLayoutManager(layoutManager);

        // Define adapter
        adapter = new ProdutoAdapter(produtos, getContext());
        recyclerProdutos.setAdapter(adapter);

        // Configurar clicks dos filtros
        view.findViewById(R.id.recicla_png).setOnClickListener(v -> filtrarTudo());
        view.findViewById(R.id.papel_png).setOnClickListener(v -> filtrarPapel());
        view.findViewById(R.id.plastico_png).setOnClickListener(v -> filtrarPlastico());
        view.findViewById(R.id.vidro_png).setOnClickListener(v -> filtrarVidro());
        view.findViewById(R.id.metal_png).setOnClickListener(v -> filtrarMetal());


        // Configurar click do botão de ordenação
        view.findViewById(R.id.button3).setOnClickListener(v -> ordenaDificuldade());
        view.findViewById(R.id.button4).setOnClickListener(v -> ordenaAvaliacoes());


        prepararProdutos();

        return view;
    }

    // Métodos de filtro (mantidos iguais)
    public void filtrarTudo() {
        adapter.filtrarPorTag("tudo");
    }

    public void filtrarPapel() {
        adapter.filtrarPorTag("papel");
    }

    public void filtrarPlastico() {
        adapter.filtrarPorTag("plastico");
    }

    public void filtrarVidro() {
        adapter.filtrarPorTag("vidro");
    }

    public void filtrarMetal() {
        adapter.filtrarPorTag("metal");
    }

    //Metodos de ordenacao
    public void ordenaDificuldade(){
        adapter.ordenaPorDificul();
    }
    public void ordenaAvaliacoes(){ adapter.ordenaPorMediaAvaliacoes(); }

    public void prepararProdutos() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("produtos");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot produtoSnapshot : dataSnapshot.getChildren()) {
                    String produtoKey = produtoSnapshot.getKey();
                    String nome = produtoSnapshot.child("nome").getValue(String.class);
                    String nivel = produtoSnapshot.child("nivel").getValue(String.class);
                    String imagem = produtoSnapshot.child("imagem").getValue(String.class);
                    double mediaAvaliacoes = produtoSnapshot.child("media_avaliacoes").getValue(Double.class);


                    // Carregar o estado de favorito
                    Boolean favoritado = produtoSnapshot.child("favoritado").getValue(Boolean.class);

                    List<String> passos = new ArrayList<>();
                    for (DataSnapshot passoSnapshot : produtoSnapshot.child("passos").getChildren()) {
                        passos.add(passoSnapshot.getValue(String.class));
                    }

                    List<String> materiais = new ArrayList<>();
                    for (DataSnapshot materialSnapshot : produtoSnapshot.child("materiais").getChildren()) {
                        materiais.add(materialSnapshot.getValue(String.class));
                    }

                    List<String> tags = new ArrayList<>();
                    for (DataSnapshot tagSnapshot : produtoSnapshot.child("tags").getChildren()) {
                        tags.add(tagSnapshot.getValue(String.class));
                    }

                    Produto produto = new Produto(nome, nivel, produtoKey, imagem, passos, materiais, tags);
                    // Definir o estado de favorito do produto
                    produto.setFavoritado(favoritado != null && favoritado);
                    produto.setMedia_avaliacoes(mediaAvaliacoes);
                    produtos.add(produto);

                }

                adapter.atualizarLista(produtos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Erro ao carregar dados", databaseError.toException());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recarrega os produtos quando voltar para o fragment
        prepararProdutos();
    }
}