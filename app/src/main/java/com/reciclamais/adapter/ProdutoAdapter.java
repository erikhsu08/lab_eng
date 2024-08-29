package com.reciclamais.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reciclamais.R;
import com.reciclamais.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>{
    private List<Produto> produtos;

    public ProdutoAdapter(List<Produto> listaProdutos){
        this.produtos = listaProdutos;
    }
    @NonNull
    @Override
    public ProdutoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoAdapter.MyViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.textNome.setText(produto.getNome());
        holder.textNivel.setText(produto.getNivel());
        holder.imagemProduto.setImageResource(produto.getImagem());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
