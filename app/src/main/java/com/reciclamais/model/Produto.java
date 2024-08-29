package com.reciclamais.model;

public class Produto {
    private String nome, nivel;
    private int imagem;


    public Produto(){

    }

    public Produto(String nome, String dificuldade, int imagem) {
        this.nome = nome;
        this.nivel = dificuldade;
        this.imagem = imagem;
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
}
