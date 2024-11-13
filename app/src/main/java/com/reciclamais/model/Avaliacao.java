package com.reciclamais.model;

public class Avaliacao {
    private float rating;
    private String comentario;
    private long timestamp;
    private String autorId;
    private String autorNome;
    private String produtoId;
    private String produtoNome; // Opcional: para exibir o nome do produto na avaliação


    public Avaliacao() {
        // Construtor vazio necessário para o Firebase
    }

    public Avaliacao(float rating, String comentario, long timestamp) {
        this.rating = rating;
        this.comentario = comentario;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    // Novos getters e setters
    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }
}