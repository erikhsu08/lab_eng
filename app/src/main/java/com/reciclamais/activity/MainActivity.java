package com.reciclamais.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reciclamais.R;
import com.reciclamais.adapter.ProdutoAdapter;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);

        //Configurar cor statusbar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cinza));

        //Definir layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProdutos.setLayoutManager(layoutManager);

        //Define adapter
        this.prepararProdutos();
        ProdutoAdapter adapter = new ProdutoAdapter(produtos);
        recyclerProdutos.setAdapter(adapter);

    } // OnCreate



    public void prepararProdutos(){
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

    public int retornaUm(){
        return 1;
    }

    //teste

}
