package com.algaworks;

public class Contato {
    private String id;
    private String nome;
    private String telefone;
    private boolean novo;

    public Contato() {
        // Construtor sem argumentos
    }

    public Contato(String id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.novo = true;
    }

    public boolean isNovo() {
        return id == null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {


        this.id = id;
    }
    public void setNovo(boolean novo) {
        this.novo = novo;
    }

}
