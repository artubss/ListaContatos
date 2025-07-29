package com.algaworks;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.function.Function;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Serviço para gerenciamento de contatos utilizando conceitos avançados de Java.
 * Implementa padrões funcionais, Streams API e Optional.
 */
@Service
public class ContatoService {
    
    private static final List<Contato> LISTA_CONTATOS = new ArrayList<>();
    
    static {
        // Inicialização com dados mais completos
        LISTA_CONTATOS.add(new Contato(1L, "João da Silva", "1199999-9999", 
            "joao@email.com", "Rua A, 123", LocalDate.of(1985, 5, 15), Categoria.FAMILIA, true));
        LISTA_CONTATOS.add(new Contato(2L, "Maria Oliveira", "1299999-9999", 
            "maria@email.com", "Rua B, 456", LocalDate.of(1990, 8, 20), Categoria.TRABALHO, false));
        LISTA_CONTATOS.add(new Contato(3L, "Pedro Santos", "1399999-9999", 
            "pedro@email.com", "Rua C, 789", LocalDate.of(1988, 3, 10), Categoria.AMIGOS, true));
        LISTA_CONTATOS.add(new Contato(4L, "Ana Souza", "1499999-9999", 
            "ana@email.com", "Rua D, 321", LocalDate.of(1992, 12, 5), Categoria.FACULDADE, false));
        LISTA_CONTATOS.add(new Contato(5L, "Carlos Oliveira", "1599999-9999", 
            "carlos@email.com", "Rua E, 654", LocalDate.of(1987, 7, 25), Categoria.ACADEMIA, true));
    }
    
    /**
     * Busca todos os contatos
     */
    public List<Contato> buscarTodos() {
        return new ArrayList<>(LISTA_CONTATOS);
    }
    
    /**
     * Busca contatos com filtros usando Streams API
     */
    public List<Contato> buscarComFiltros(ContatoFiltro filtro) {
        return LISTA_CONTATOS.stream()
                .filter(criarPredicadoFiltro(filtro))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca contatos por texto (nome, telefone, email)
     */
    public List<Contato> buscarPorTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return buscarTodos();
        }
        
        String textoLower = texto.toLowerCase();
        return LISTA_CONTATOS.stream()
                .filter(contato -> 
                    contato.getNome().toLowerCase().contains(textoLower) ||
                    contato.getTelefone().contains(texto) ||
                    (contato.getEmail() != null && contato.getEmail().toLowerCase().contains(textoLower)))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca contatos favoritos
     */
    public List<Contato> buscarFavoritos() {
        return LISTA_CONTATOS.stream()
                .filter(Contato::isFavorito)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca contatos por categoria
     */
    public List<Contato> buscarPorCategoria(Categoria categoria) {
        return LISTA_CONTATOS.stream()
                .filter(contato -> contato.getCategoria() == categoria)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca contato por ID usando Optional
     */
    public Optional<Contato> buscarPorId(Long id) {
        return LISTA_CONTATOS.stream()
                .filter(contato -> Objects.equals(contato.getId(), id))
                .findFirst();
    }
    
    /**
     * Salva um novo contato
     */
    public Contato salvar(Contato contato) {
        if (contato.getId() == null) {
            // Gera ID único
            Long nextId = LISTA_CONTATOS.stream()
                    .mapToLong(Contato::getId)
                    .max()
                    .orElse(0L) + 1L;
            contato.setId(nextId);
        }
        
        contato.setDataCriacao(LocalDateTime.now());
        contato.setDataAtualizacao(LocalDateTime.now());
        
        LISTA_CONTATOS.add(contato);
        return contato;
    }
    
    /**
     * Atualiza um contato existente
     */
    public Optional<Contato> atualizar(Long id, Contato contatoAtualizado) {
        return buscarPorId(id).map(contatoExistente -> {
            contatoExistente.setNome(contatoAtualizado.getNome());
            contatoExistente.setTelefone(contatoAtualizado.getTelefone());
            contatoExistente.setEmail(contatoAtualizado.getEmail());
            contatoExistente.setEndereco(contatoAtualizado.getEndereco());
            contatoExistente.setDataNascimento(contatoAtualizado.getDataNascimento());
            contatoExistente.setCategoria(contatoAtualizado.getCategoria());
            contatoExistente.setFavorito(contatoAtualizado.isFavorito());
            contatoExistente.setDataAtualizacao(LocalDateTime.now());
            return contatoExistente;
        });
    }
    
    /**
     * Remove um contato
     */
    public boolean remover(Long id) {
        return LISTA_CONTATOS.removeIf(contato -> Objects.equals(contato.getId(), id));
    }
    
    /**
     * Marca/desmarca contato como favorito
     */
    public Optional<Contato> alternarFavorito(Long id) {
        return buscarPorId(id).map(contato -> {
            if (contato.isFavorito()) {
                contato.desmarcarComoFavorito();
            } else {
                contato.marcarComoFavorito();
            }
            return contato;
        });
    }
    
    /**
     * Obtém estatísticas dos contatos
     */
    public Map<String, Object> obterEstatisticas() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("totalContatos", LISTA_CONTATOS.size());
        estatisticas.put("contatosFavoritos", LISTA_CONTATOS.stream().filter(Contato::isFavorito).count());
        
        // Contagem por categoria
        Map<Categoria, Long> contagemPorCategoria = LISTA_CONTATOS.stream()
                .collect(Collectors.groupingBy(Contato::getCategoria, Collectors.counting()));
        estatisticas.put("contagemPorCategoria", contagemPorCategoria);
        
        // Média de idade
        double mediaIdade = LISTA_CONTATOS.stream()
                .mapToInt(Contato::getIdade)
                .average()
                .orElse(0.0);
        estatisticas.put("mediaIdade", mediaIdade);
        
        return estatisticas;
    }
    
    /**
     * Cria predicado dinâmico baseado nos filtros
     */
    private Predicate<Contato> criarPredicadoFiltro(ContatoFiltro filtro) {
        return contato -> {
            // Filtro por nome
            if (filtro.getNome().isPresent() && !contato.getNome().toLowerCase()
                    .contains(filtro.getNome().get().toLowerCase())) {
                return false;
            }
            
            // Filtro por telefone
            if (filtro.getTelefone().isPresent() && !contato.getTelefone()
                    .contains(filtro.getTelefone().get())) {
                return false;
            }
            
            // Filtro por email
            if (filtro.getEmail().isPresent() && (contato.getEmail() == null || 
                    !contato.getEmail().toLowerCase().contains(filtro.getEmail().get().toLowerCase()))) {
                return false;
            }
            
            // Filtro por categoria
            if (filtro.getCategoria().isPresent() && contato.getCategoria() != filtro.getCategoria().get()) {
                return false;
            }
            
            // Filtro por favorito
            if (filtro.getFavorito().isPresent() && contato.isFavorito() != filtro.getFavorito().get()) {
                return false;
            }
            
            // Filtro por data de nascimento
            if (filtro.getDataNascimentoInicio().isPresent() && 
                    (contato.getDataNascimento() == null || 
                     contato.getDataNascimento().isBefore(filtro.getDataNascimentoInicio().get()))) {
                return false;
            }
            
            if (filtro.getDataNascimentoFim().isPresent() && 
                    (contato.getDataNascimento() == null || 
                     contato.getDataNascimento().isAfter(filtro.getDataNascimentoFim().get()))) {
                return false;
            }
            
            return true;
        };
    }
    
    /**
     * Obtém todas as categorias disponíveis
     */
    public List<Categoria> obterCategorias() {
        return Arrays.asList(Categoria.values());
    }
}