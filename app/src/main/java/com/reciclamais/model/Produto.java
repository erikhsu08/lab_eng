package com.reciclamais.model;

import java.util.List;

public class Produto {
    private String nome, nivel, key;
    private String imagem;
    private List<String> passos, materiais, tags;
    private boolean favoritado;

    // Novos campos
    private String autorId;
    private String autorNome;
    private float media_avaliacoes;
    private int total_avaliacoes;


    public Produto(){

    }

    public Produto(String nome, String dificuldade, String key, String imagem, List<String> passos, List<String> materiais, List<String> tags) {
        this.nome = nome;
        this.nivel = dificuldade;
        this.key = key;
        this.imagem = imagem;
        this.passos = passos;
        this.materiais = materiais;
        this.tags = tags;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public String getKey() { return key; }

    public void setKey(String key){
        this.key = key;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getPassos() {
        return passos;
    }

    public void setPassos(List<String> passos) {
        this.passos = passos;
    }

    public List<String> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<String> materiais) {
        this.materiais = materiais;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isFavoritado() {
        return favoritado;
    }

    public void setFavoritado(boolean favoritado) {
        this.favoritado = favoritado;
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

    public float getMedia_avaliacoes() {
        return media_avaliacoes;
    }

    public void setMedia_avaliacoes(float media_avaliacoes) {
        this.media_avaliacoes = media_avaliacoes;
    }

    public int getTotal_avaliacoes() {
        return total_avaliacoes;
    }

    public void setTotal_avaliacoes(int total_avaliacoes) {
        this.total_avaliacoes = total_avaliacoes;
    }

}