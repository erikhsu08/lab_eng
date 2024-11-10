package com.reciclamais.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reciclamais.R;
import com.reciclamais.adapter.ComentarioAdapter;
import com.reciclamais.model.Avaliacao;
import com.reciclamais.model.Comentario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDetalhadoActivity extends AppCompatActivity {

    private TextView produtoNome, produtoNivel;
    private ImageView produtoImagem;
    private LinearLayout layoutPassos, layoutMateriais, layoutTags;

    private RatingBar produtoRating;
    private EditText comentarioInput;
    private Button btnEnviarAvaliacao;
    private DatabaseReference databaseReference;
    private String produtoId; // ID do produto
    private String produtoKey; // Nova variável para armazenar a chave do produto

    private RecyclerView recyclerComentarios;
    private ComentarioAdapter comentarioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_detalhado);

        produtoNome = findViewById(R.id.produtoNome);
        produtoNivel = findViewById(R.id.produtoDescricao);
        produtoImagem = findViewById(R.id.produtoImagem);
        layoutPassos = findViewById(R.id.layoutPassos);
        layoutMateriais = findViewById(R.id.layoutMateriais);
        layoutTags = findViewById(R.id.layoutTags);

        produtoRating = findViewById(R.id.produtoRating);
        comentarioInput = findViewById(R.id.comentarioInput);
        btnEnviarAvaliacao = findViewById(R.id.btnEnviarAvaliacao);



        // Inicializa o Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnEnviarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarAvaliacao();
            }
        });

        // Inicializar RecyclerView e Adapter
        recyclerComentarios = findViewById(R.id.recyclerComentarios);
        comentarioAdapter = new ComentarioAdapter(this);
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerComentarios.setAdapter(comentarioAdapter);

        // Recebe os dados passados pela Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            produtoKey = extras.getString("produtoKey"); // Nova chave do produto
            produtoId = extras.getString("nome"); // Usamos o nome do produto como ID

            produtoNome.setText(produtoId);
            produtoNivel.setText(extras.getString("nivel"));

            // Carrega a imagem com Glide e aplica o placeholder
            String imagemResource = extras.getString("imagem");
            Glide.with(this)
                    .load(imagemResource)
                    .placeholder(R.drawable.loading) // substitua pelo ID do placeholder
                    .into(produtoImagem);

            ArrayList<String> passos = extras.getStringArrayList("passos");
            ArrayList<String> materiais = extras.getStringArrayList("materiais");
            ArrayList<String> tags = extras.getStringArrayList("tags");

            // Define uma cor para o texto dos passos, materiais e tags
            int textColor = getResources().getColor(R.color.black);
            float textSize = 16f; // Define o tamanho da fonte em sp

            if (passos != null) {
                for (int i = 0; i < passos.size(); i++) {
                    TextView textView = new TextView(this);
                    textView.setText((i + 1) + ". " + passos.get(i));
                    textView.setTextColor(textColor); // Define a cor do texto
                    textView.setTextSize(textSize); // Define o tamanho do texto
                    layoutPassos.addView(textView);
                }
            }

            // Adiciona os materiais ao layoutMateriais com a cor definida
            if (materiais != null) {
                for (String material : materiais) {
                    TextView textView = new TextView(this);
                    textView.setText("• " + material);
                    textView.setTextColor(textColor); // Define a cor do texto
                    textView.setTextSize(textSize); // Define o tamanho do texto
                    layoutMateriais.addView(textView);
                }
            }

            // Adiciona as tags ao layoutTags com a cor definida
            if (tags != null) {
                for (String tag : tags) {
                    TextView textView = new TextView(this);
                    textView.setText("#" + tag + " ");
                    textView.setTextColor(textColor); // Define a cor do texto
                    textView.setTextSize(textSize); // Define o tamanho do texto
                    textView.setPadding(0, 0, 10, 0);  // Adiciona um pequeno espaçamento à direita
                    layoutTags.addView(textView);
                }
            }
        }

        // Configurar listener do botão de enviar avaliação
        btnEnviarAvaliacao.setOnClickListener(v -> enviarAvaliacao());

        // Se você tiver o produtoKey, carregue os comentários
        if (produtoKey != null) {
            carregarComentarios();
        }
    }

    private void carregarComentarios() {
        DatabaseReference avaliacoesRef = databaseReference
                .child("produtos")
                .child(produtoKey)
                .child("avaliacoes");

        avaliacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comentario> comentarios = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comentario comentario = snapshot.getValue(Comentario.class);
                    if (comentario != null) {
                        comentarios.add(comentario);
                    }
                }

                // Ordenar comentários pelo timestamp (mais recentes primeiro)
                Collections.sort(comentarios, (c1, c2) ->
                        Long.compare(c2.getTimestamp(), c1.getTimestamp()));

                comentarioAdapter.atualizarComentarios(comentarios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProdutoDetalhadoActivity.this,
                        "Erro ao carregar comentários: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarAvaliacao() {
        float rating = produtoRating.getRating();
        String comentario = comentarioInput.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Por favor, dê uma avaliação", Toast.LENGTH_SHORT).show();
            return;
        }

        if (produtoKey == null) {
            Toast.makeText(this, "Erro ao identificar o produto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar objeto de avaliação
        Avaliacao avaliacao = new Avaliacao(
                rating,
                comentario,
                System.currentTimeMillis()
        );

        // Referência para o produto específico
        DatabaseReference produtoRef = databaseReference.child("produtos").child(produtoKey);

        // Gerar uma chave única para a avaliação
        String avaliacaoId = produtoRef.child("avaliacoes").push().getKey();

        if (avaliacaoId != null) {
            // Criar mapa de atualizações
            Map<String, Object> atualizacoes = new HashMap<>();
            atualizacoes.put("/avaliacoes/" + avaliacaoId, avaliacao);

            // Atualizar a média e total de avaliações
            produtoRef.get().addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    // Obter valores atuais
                    long totalAvaliacoes = snapshot.child("total_avaliacoes").getValue(Long.class) != null ?
                            snapshot.child("total_avaliacoes").getValue(Long.class) : 0;
                    double mediaAtual = snapshot.child("media_avaliacoes").getValue(Double.class) != null ?
                            snapshot.child("media_avaliacoes").getValue(Double.class) : 0.0;

                    // Calcular nova média
                    double novaMedia = ((mediaAtual * totalAvaliacoes) + rating) / (totalAvaliacoes + 1);

                    // Adicionar média e total às atualizações
                    atualizacoes.put("/media_avaliacoes", novaMedia);
                    atualizacoes.put("/total_avaliacoes", totalAvaliacoes + 1);

                    // Realizar todas as atualizações
                    produtoRef.updateChildren(atualizacoes)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(ProdutoDetalhadoActivity.this,
                                        "Avaliação enviada com sucesso!", Toast.LENGTH_SHORT).show();
                                limparCamposAvaliacao();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(ProdutoDetalhadoActivity.this,
                                        "Erro ao enviar avaliação", Toast.LENGTH_SHORT).show();
                            });
                }
            });
        }
    }

    private void limparCamposAvaliacao() {
        produtoRating.setRating(0);
        comentarioInput.setText("");
    }
}
