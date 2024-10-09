package com.reciclamais.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.reciclamais.R;

import java.util.ArrayList;

public class ProdutoDetalhadoActivity extends AppCompatActivity {

    private TextView produtoNome, produtoNivel;
    private ImageView produtoImagem;
    private LinearLayout layoutPassos, layoutMateriais, layoutTags;

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

        // Recebe os dados passados pela Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            produtoNome.setText(extras.getString("nome"));
            produtoNivel.setText(extras.getString("nivel"));
            produtoImagem.setImageResource(extras.getInt("imagem"));

            ArrayList<String> passos = extras.getStringArrayList("passos");
            ArrayList<String> materiais = extras.getStringArrayList("materiais");
            ArrayList<String> tags = extras.getStringArrayList("tags");

            if (passos != null) {
                for (int i = 0; i < passos.size(); i++) {
                    TextView textView = new TextView(this);
                    textView.setText((i + 1) + ". " + passos.get(i));
                    layoutPassos.addView(textView);
                }
            }

            if (materiais != null) {
                for (String material : materiais) {
                    TextView textView = new TextView(this);
                    textView.setText("• " + material);
                    layoutMateriais.addView(textView);
                }
            }

            if (tags != null) {
                for (String tag : tags) {
                    TextView textView = new TextView(this);
                    textView.setText("#" + tag + " ");
                    textView.setPadding(0, 0, 10, 0);  // Adiciona um pequeno espaçamento à direita
                    layoutTags.addView(textView);
                }
            }
        }
    }
}