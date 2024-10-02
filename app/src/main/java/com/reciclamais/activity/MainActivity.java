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
    import com.google.firebase.FirebaseApp;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.reciclamais.R;
    import com.reciclamais.adapter.ProdutoAdapter;
    import com.reciclamais.model.Produto;

    import java.util.ArrayList;
    import java.util.List;

    public class MainActivity extends AppCompatActivity {
        private RecyclerView recyclerProdutos;
        private List<Produto> produtos = new ArrayList<>();
        private Button add_produto;
        private BottomNavigationView bottomNavigationView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            // Inicializa o Firebase
            FirebaseApp.initializeApp(this);

            recyclerProdutos = findViewById(R.id.recyclerProdutos);
            add_produto = findViewById(R.id.add_produto);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            // Configurar cor statusbar
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cinza));

            // Definir layout
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerProdutos.setLayoutManager(layoutManager);

            // Define adapter
            this.prepararProdutos();
            ProdutoAdapter adapter = new ProdutoAdapter(produtos);
            recyclerProdutos.setAdapter(adapter);


        }

        public void prepararProdutos() {
            Produto p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);

            p = new Produto("Flor de papel", "Nível: fácil", R.drawable.flor);
            this.produtos.add(p);
        }
    }
