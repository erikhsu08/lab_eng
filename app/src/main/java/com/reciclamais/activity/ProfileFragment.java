package com.reciclamais.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reciclamais.R;
import com.reciclamais.adapter.ComentarioAdapter;
import com.reciclamais.adapter.ProdutoAdapter;
import com.reciclamais.model.Avaliacao;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView profileUserName;
    private TextView profileUserBio;
    private TextView tabPublicacoes;
    private TextView tabAvaliacoes;
    private RecyclerView recyclerViewPublicacoes;
    private RecyclerView recyclerViewAvaliacoes;

    private ProdutoAdapter produtoAdapter;
    private ComentarioAdapter avaliacaoAdapter;

    private List<Produto> listaProdutos;
    private List<Avaliacao> listaAvaliacoes;

    private DatabaseReference databaseRef;
    private static final String USER_ID = "user123"; // ID do João Silva

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        inicializarComponentes(view);
        inicializarListas();
        configurarRecyclerViews();
        configurarTabs();
        carregarDadosUsuario();
        carregarProdutos();
        carregarAvaliacoes();

        return view;
    }

    private void inicializarComponentes(View view) {
        profileImage = view.findViewById(R.id.profileImage);
        profileUserName = view.findViewById(R.id.profileUserName);
        profileUserBio = view.findViewById(R.id.profileUserBio);
        tabPublicacoes = view.findViewById(R.id.tabPublicacoes);
        tabAvaliacoes = view.findViewById(R.id.tabAvaliacoes);
        recyclerViewPublicacoes = view.findViewById(R.id.recyclerViewPublicacoes);
        recyclerViewAvaliacoes = view.findViewById(R.id.recyclerViewAvaliacoes);

        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    private void inicializarListas() {
        listaProdutos = new ArrayList<>();
        listaAvaliacoes = new ArrayList<>();

        produtoAdapter = new ProdutoAdapter(listaProdutos, getContext());
        avaliacaoAdapter = new ComentarioAdapter(getContext());

    }

    private void configurarRecyclerViews() {
        // Configurar RecyclerView de produtos
        LinearLayoutManager layoutManagerProdutos = new LinearLayoutManager(getContext());
        recyclerViewPublicacoes.setLayoutManager(layoutManagerProdutos);
        recyclerViewPublicacoes.setHasFixedSize(true);
        recyclerViewPublicacoes.setAdapter(produtoAdapter);

        // Configurar RecyclerView de avaliações
        LinearLayoutManager layoutManagerAvaliacoes = new LinearLayoutManager(getContext());
        recyclerViewAvaliacoes.setLayoutManager(layoutManagerAvaliacoes);
        recyclerViewAvaliacoes.setHasFixedSize(true);
        recyclerViewAvaliacoes.setAdapter(avaliacaoAdapter);
    }

    private void configurarTabs() {
        recyclerViewAvaliacoes.setVisibility(View.GONE);

        tabPublicacoes.setOnClickListener(v -> {
            recyclerViewPublicacoes.setVisibility(View.VISIBLE);
            recyclerViewAvaliacoes.setVisibility(View.GONE);
            tabPublicacoes.setTextColor(getResources().getColor(R.color.black));
            tabAvaliacoes.setTextColor(getResources().getColor(android.R.color.darker_gray));
        });

        tabAvaliacoes.setOnClickListener(v -> {
            recyclerViewPublicacoes.setVisibility(View.GONE);
            recyclerViewAvaliacoes.setVisibility(View.VISIBLE);
            tabPublicacoes.setTextColor(getResources().getColor(android.R.color.darker_gray));
            tabAvaliacoes.setTextColor(getResources().getColor(R.color.black));
        });
    }

    private void carregarDadosUsuario() {
        databaseRef.child("usuarios").child(USER_ID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String nome = snapshot.child("nome").getValue(String.class);
                            String bio = snapshot.child("bio").getValue(String.class);
                            String foto = snapshot.child("foto").getValue(String.class);

                            profileUserName.setText(nome);
                            profileUserBio.setText(bio);
                            // Aqui você pode usar Glide ou Picasso para carregar a foto
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Tratar erro
                    }
                });
    }

    private void carregarProdutos() {
        databaseRef.child("produtos").orderByChild("autorId").equalTo(USER_ID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaProdutos.clear();

                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Produto produto = productSnapshot.getValue(Produto.class);
                            if (produto != null) {
                                // Garantir que a key do produto seja definida
                                produto.setKey(productSnapshot.getKey());
                                listaProdutos.add(produto);
                            }
                        }

                        produtoAdapter.atualizarLista(listaProdutos);

                        // Adicionar log para debug
                        Log.d("ProfileFragment", "Produtos carregados: " + listaProdutos.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("ProfileFragment", "Erro ao carregar produtos: " + error.getMessage());
                    }
                });
    }

    private void carregarAvaliacoes() {
        databaseRef.child("produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaAvaliacoes.clear();

                for (DataSnapshot produtoSnapshot : snapshot.getChildren()) {
                    // Pegar o nome do produto para referência
                    String nomeProduto = produtoSnapshot.child("nome").getValue(String.class);
                    String imagemProduto = produtoSnapshot.child("imagem").getValue(String.class);

                    // Navegar para o nó de avaliações deste produto
                    DataSnapshot avaliacoesSnapshot = produtoSnapshot.child("avaliacoes");

                    for (DataSnapshot avaliacaoSnapshot : avaliacoesSnapshot.getChildren()) {
                        String autorId = avaliacaoSnapshot.child("autorId").getValue(String.class);

                        // Verificar se a avaliação foi feita pelo usuário atual
                        if (autorId != null && autorId.equals(USER_ID)) {
                            Avaliacao avaliacao = new Avaliacao();
                            avaliacao.setProdutoId(produtoSnapshot.getKey());
                            avaliacao.setProdutoNome(nomeProduto);
                            //avaliacao.setProdutoImagem(imagemProduto);
                            avaliacao.setAutorId(autorId);
                            avaliacao.setAutorNome(avaliacaoSnapshot.child("autorNome").getValue(String.class));
                            avaliacao.setComentario(avaliacaoSnapshot.child("comentario").getValue(String.class));
                            avaliacao.setRating(avaliacaoSnapshot.child("rating").getValue(Integer.class));
                            avaliacao.setTimestamp(avaliacaoSnapshot.child("timestamp").getValue(Long.class));

                            listaAvaliacoes.add(avaliacao);
                        }
                    }
                }

                avaliacaoAdapter.atualizarComentarios(listaAvaliacoes);

                // Adicionar log para debug
                Log.d("ProfileFragment", "Avaliações carregadas: " + listaAvaliacoes.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "Erro ao carregar avaliações: " + error.getMessage());
            }
        });
    }
}