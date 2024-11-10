package com.reciclamais.model;

public class Comentario {
    private float rating;
    private String comentario;
    private long timestamp;

    public Comentario() {} // Required for Firebase

    public Comentario(float rating, String comentario, long timestamp) {
        this.rating = rating;
        this.comentario = comentario;
        this.timestamp = timestamp;
    }

    // Getters
    public float getRating() { return rating; }
    public String getComentario() { return comentario; }
    public long getTimestamp() { return timestamp; }
}
