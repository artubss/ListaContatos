package com.algaworks;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Classe que representa filtros para busca de contatos.
 * Utiliza conceitos avançados de Java como Builder Pattern e Optional.
 */
public class ContatoFiltro {
    
    private Optional<String> nome = Optional.empty();
    private Optional<String> telefone = Optional.empty();
    private Optional<String> email = Optional.empty();
    private Optional<Categoria> categoria = Optional.empty();
    private Optional<Boolean> favorito = Optional.empty();
    private Optional<LocalDate> dataNascimentoInicio = Optional.empty();
    private Optional<LocalDate> dataNascimentoFim = Optional.empty();
    
    // Construtor privado para Builder Pattern
    private ContatoFiltro() {}
    
    // Getters
    public Optional<String> getNome() { return nome; }
    public Optional<String> getTelefone() { return telefone; }
    public Optional<String> getEmail() { return email; }
    public Optional<Categoria> getCategoria() { return categoria; }
    public Optional<Boolean> getFavorito() { return favorito; }
    public Optional<LocalDate> getDataNascimentoInicio() { return dataNascimentoInicio; }
    public Optional<LocalDate> getDataNascimentoFim() { return dataNascimentoFim; }
    
    /**
     * Builder Pattern para construção fluente do filtro
     */
    public static class Builder {
        private ContatoFiltro filtro = new ContatoFiltro();
        
        public Builder nome(String nome) {
            filtro.nome = Optional.ofNullable(nome);
            return this;
        }
        
        public Builder telefone(String telefone) {
            filtro.telefone = Optional.ofNullable(telefone);
            return this;
        }
        
        public Builder email(String email) {
            filtro.email = Optional.ofNullable(email);
            return this;
        }
        
        public Builder categoria(Categoria categoria) {
            filtro.categoria = Optional.ofNullable(categoria);
            return this;
        }
        
        public Builder favorito(Boolean favorito) {
            filtro.favorito = Optional.ofNullable(favorito);
            return this;
        }
        
        public Builder dataNascimentoInicio(LocalDate data) {
            filtro.dataNascimentoInicio = Optional.ofNullable(data);
            return this;
        }
        
        public Builder dataNascimentoFim(LocalDate data) {
            filtro.dataNascimentoFim = Optional.ofNullable(data);
            return this;
        }
        
        public ContatoFiltro build() {
            return filtro;
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Verifica se o filtro está vazio (sem critérios)
     */
    public boolean isEmpty() {
        return nome.isEmpty() && telefone.isEmpty() && email.isEmpty() && 
               categoria.isEmpty() && favorito.isEmpty() && 
               dataNascimentoInicio.isEmpty() && dataNascimentoFim.isEmpty();
    }
    
    @Override
    public String toString() {
        return "ContatoFiltro{" +
                "nome=" + nome.orElse("null") +
                ", telefone=" + telefone.orElse("null") +
                ", email=" + email.orElse("null") +
                ", categoria=" + categoria.map(Categoria::getDescricao).orElse("null") +
                ", favorito=" + favorito.orElse(null) +
                '}';
    }
}