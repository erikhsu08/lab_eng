package com.reciclamais.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reciclamais.R;
import com.reciclamais.activity.ProdutoDetalhadoActivity;
import com.reciclamais.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados;
    private Context context;

    public ProdutoAdapter(List<Produto> listaProdutos, Context context) {
        this.produtos = (listaProdutos != null) ? listaProdutos : new ArrayList<>();
        this.produtosFiltrados = new ArrayList<>(this.produtos);
        this.context = context;
    }

    public void filtrarPorTag(String tag) {
        produtosFiltrados.clear();
        if (tag.equals("tudo")) {
            produtosFiltrados.addAll(produtos);
        } else {
            for (Produto produto : produtos) {
                if (produto.getTags() != null && produto.getTags().contains(tag.toLowerCase())) {
                    produtosFiltrados.add(produto);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void atualizarLista(List<Produto> novosProdutos) {
        this.produtos = novosProdutos;
        this.produtosFiltrados = new ArrayList<>(this.produtos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdutoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoAdapter.MyViewHolder holder, int position) {
        Produto produto = produtosFiltrados.get(position);
        holder.textNome.setText(produto.getNome());
        holder.textNivel.setText(produto.getNivel());
        holder.imagemProduto.setImageResource(produto.getImagem());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProdutoDetalhadoActivity.class);
            intent.putExtra("nome", produto.getNome());
            intent.putExtra("nivel", produto.getNivel());
            intent.putExtra("imagem", produto.getImagem());
            intent.putStringArrayListExtra("passos", new ArrayList<>(produto.getPassos()));
            intent.putStringArrayListExtra("materiais", new ArrayList<>(produto.getMateriais()));
            intent.putStringArrayListExtra("tags", new ArrayList<>(produto.getTags()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return produtosFiltrados.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemProduto;
        TextView textNome, textNivel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            textNivel = itemView.findViewById(R.id.textNivel);
            imagemProduto = itemView.findViewById(R.id.imageProduto);
        }
    }
}