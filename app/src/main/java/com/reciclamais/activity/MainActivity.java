    package com.reciclamais.activity;


    import androidx.fragment.app.Fragment;
    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import androidx.activity.EdgeToEdge;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.reciclamais.R;
    import com.reciclamais.adapter.ProdutoAdapter;
    import com.reciclamais.model.Produto;

    import java.util.ArrayList;
    import java.util.List;

    public class MainActivity extends AppCompatActivity {
        private RecyclerView recyclerProdutos;
        private List<Produto> produtos = new ArrayList<>();
        private BottomNavigationView bottomNavigationView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            recyclerProdutos = findViewById(R.id.recyclerProdutos);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            // Configurar cor statusbar
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cinza));

            // Definir layout
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerProdutos.setLayoutManager(layoutManager);

            // Define adapter
            this.prepararProdutos();
            ProdutoAdapter adapter = new ProdutoAdapter(produtos, this);
            recyclerProdutos.setAdapter(adapter);


        }

        public void prepararProdutos() {
            //Definir passos, materiais e tags
            List<String> passos1 = List.of("Lave e seque uma caixa de suco.", "Corte a caixa na altura desejada para o porta-lápis.", "Decore o exterior da caixa com papel colorido, tecido ou pintura.",
                    "Para maior durabilidade, passe uma camada de cola branca ou verniz sobre a decoração.", "Coloque os lápis e canetas e organize sua mesa de maneira sustentável.");

            List<String> materiais1 = List.of("Caixa de suco", "Papel colorido", "Cola", " Tesoura", "Verniz", "Cola Branca");

            List<String> tags1 = List.of("Papel", "Papelao", "Decoracao");


            Produto p = new Produto("Porta-Lápis com Caixa de Suco", "Nível: Fácil", R.drawable.portalapis, passos1, materiais1, tags1);
            this.produtos.add(p);

            //Definir passos, materiais e tags
            passos1 = List.of("Lave e seque os palitos de sorvete.", "Cole os palitos de forma a criar as laterais da luminária, intercalando-os em padrões quadrados ou triangulares.", "Decore o exterior da caixa com papel colorido, tecido ou pintura.",
                    "Cole as extremidades com cola quente para formar a estrutura.", "Coloque uma base de madeira ou papelão resistente para o fundo.", "Coloque uma pequena luz LED no centro da luminária.");

            materiais1 = List.of("Palitos de sorvete", "cola quente", "lâmpada LED", "Base de Papelão ou Madeira");

            tags1 = List.of("Madeira", "Palitos de Sorvete", "Decoração", "Iluminação");

            p = new Produto("Luminária com Palitos de Sorvete", "Nível: Médio", R.drawable.luminaria, passos1, materiais1, tags1);
            this.produtos.add(p);

            //Definir passos, materiais e tags
            passos1 = List.of("Corte a garrafa PET ao meio, mantendo a parte inferior para o vaso.", "Faça pequenos furos no fundo da garrafa para drenagem da água.", "Decore o exterior do vaso com tintas, adesivos ou fitas coloridas.",
                    "Preencha o vaso com terra e plante as sementes ou mudas.", "Coloque em um local bem iluminado e regue conforme necessário.");

            materiais1 = List.of("Garrafa PET", "Tesoura", "Tinta ou adesivos para decoração", "Terra", "Sementes ou mudas");

            tags1 = List.of("Plastico", "Garrafa PET", "Decoração", "Jardinagem");

            p = new Produto("Vasinho de Plantas com Garrafa PET", "Nível: Fácil", R.drawable.vasopet, passos1, materiais1, tags1);
            this.produtos.add(p);

            //Definir passos, materiais e tags
            passos1 = List.of("Corte as rolhas ao meio no sentido longitudinal.", "Cole as rolhas lado a lado sobre uma base de madeira ou papelão para criar um painel.", "Use pinos ou ganchos pequenos para pendurar brincos e colares no painel.",
                    "Para anéis, crie cortes finos nas rolhas onde os anéis possam ser encaixados.", "Pendure o painel na parede ou mantenha sobre uma mesa.");

            materiais1 = List.of("Rolhas de vinho", "cola quente", "pinos ou ganchos", "Base de Papelão ou Madeira");

            tags1 = List.of("cortiça", "rolhas", "organização", "acessórios");

            p = new Produto("Porta-joias com Rolhas de Vinho", "Nível: Médio", R.drawable.portajoias, passos1, materiais1, tags1);
            this.produtos.add(p);

            //Definir passos, materiais e tags
            passos1 = List.of("Corte as seções individuais da caixa de ovos para formar pequenos “cálices”.", "Pinte cada uma das peças com tinta acrílica ou spray para um efeito colorido.", "Cole as peças ao redor de uma estrutura de arame ou diretamente sobre uma lâmpada LED de baixa potência.",
                    "Conecte o abajur a uma base de iluminação e ligue para testar o efeito.");

            materiais1 = List.of("Caixa de ovos", "cola quente", "lâmpada LED", "tinta acrílica", "estrutura de arame.");

            tags1 = List.of("papelão", "caixa de ovos", "iluminação", "decoração");

            p = new Produto("Abajur com Caixa de Ovos", "Nível: Difícil", R.drawable.abajur, passos1, materiais1, tags1);
            this.produtos.add(p);

            /*
            p = new Produto("nome", "nivel", R.drawable.img);
            this.produtos.add(p);

            p = new Produto("nome", "nivel", R.drawable.img);
            this.produtos.add(p);

            p = new Produto("nome", "nivel", R.drawable.teste);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p); */
        }
    }
