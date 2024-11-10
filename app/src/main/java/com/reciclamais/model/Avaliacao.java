package com.reciclamais.model;

public class Avaliacao {
    private float rating;
    private String comentario;
    private long timestamp;

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
}