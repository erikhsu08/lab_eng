package com.reciclamais.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reciclamais.R;
import com.reciclamais.model.Avaliacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {
    private List<Avaliacao> comentarios;
    private Context context;
    private SimpleDateFormat dateFormat;


    public ComentarioAdapter(Context context) {
        this.context = context;
        this.comentarios = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comentario, parent, false);
        return new ComentarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        Avaliacao comentario = comentarios.get(position);

        // Set rating
        holder.quantidadeEstrelas.setText(String.valueOf((int)comentario.getRating()));

        // Set comment text
        holder.comentarioTexto.setText(comentario.getComentario());

        // Format and set date
        String dataFormatada = dateFormat.format(new Date(comentario.getTimestamp()));
        holder.dataAvaliacao.setText(dataFormatada);

        // For now, we'll use a default user name since we don't have user information
        holder.usuarioNome.setText("João Silva");
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public void atualizarComentarios(List<Avaliacao> novosComentarios) {
        this.comentarios.clear();
        this.comentarios.addAll(novosComentarios);
        notifyDataSetChanged();
    }

    static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        TextView usuarioNome, comentarioTexto, quantidadeEstrelas, dataAvaliacao;
        ImageView usuarioFoto, estrelaIcone;

        ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            usuarioNome = itemView.findViewById(R.id.usuarioNome);
            comentarioTexto = itemView.findViewById(R.id.comentarioTexto);
            quantidadeEstrelas = itemView.findViewById(R.id.quantidadeEstrelas);
            dataAvaliacao = itemView.findViewById(R.id.dataAvaliacao);
            usuarioFoto = itemView.findViewById(R.id.usuarioFoto);
            estrelaIcone = itemView.findViewById(R.id.estrelaIcone);
        }
    }
}