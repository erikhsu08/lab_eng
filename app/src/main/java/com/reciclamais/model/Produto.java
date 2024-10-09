package com.reciclamais.model;

import java.util.List;

public class Produto {
    private String nome, nivel;
    private int imagem;
    private List<String> passos, materiais, tags;


    public Produto(){

    }

    public Produto(String nome, String dificuldade, int imagem, List<String> passos, List<String> materiais, List<String> tags) {
        this.nome = nome;
        this.nivel = dificuldade;
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

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
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
}
