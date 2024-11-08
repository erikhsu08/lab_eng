package com.reciclamais.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
    }


}