package com.algaworks;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Serviço para gerenciamento de contatos utilizando conceitos avançados de
 * Java. Implementa padrões funcionais, Streams API e Optional.
 */
@Service
public class ContatoService {

    private static final Logger logger = LoggerFactory.getLogger(ContatoService.class);

    private static final List<Contato> LISTA_CONTATOS = new ArrayList<>();

    static {
        // Dados mock iniciais com novos campos
        LISTA_CONTATOS.add(new Contato(1L, "João Silva", "(11) 99999-9999",
                "joao@email.com", "Rua A, 123", LocalDate.of(1990, 5, 15), Categoria.FAMILIA, true));
        LISTA_CONTATOS.add(new Contato(2L, "Maria Santos", "(11) 88888-8888",
                "maria@email.com", "Av B, 456", LocalDate.of(1985, 8, 20), Categoria.TRABALHO, false));
        LISTA_CONTATOS.add(new Contato(3L, "Pedro Costa", "(11) 77777-7777",
                "pedro@email.com", "Rua C, 789", LocalDate.of(1995, 3, 10), Categoria.AMIGOS, true));
        LISTA_CONTATOS.add(new Contato(4L, "Ana Oliveira", "(11) 66666-6666",
                "ana@email.com", "Av D, 321", LocalDate.of(1988, 12, 25), Categoria.FACULDADE, false));
        LISTA_CONTATOS.add(new Contato(5L, "Carlos Lima", "(11) 55555-5555",
                "carlos@email.com", "Rua E, 654", LocalDate.of(1992, 7, 8), Categoria.ACADEMIA, true));

        logger.info("ContatoService inicializado com {} contatos mock", LISTA_CONTATOS.size());
    }

    /**
     * Busca todos os contatos
     */
    public List<Contato> buscarTodos() {
        logger.debug("Buscando todos os contatos");
        List<Contato> contatos = new ArrayList<>(LISTA_CONTATOS);
        logger.info("Retornando {} contatos", contatos.size());
        return contatos;
    }

    /**
     * Busca contatos com filtros usando Streams API
     */
    public List<Contato> buscarComFiltros(ContatoFiltro filtro) {
        logger.debug("Buscando contatos com filtros: {}", filtro);

        List<Contato> contatos = LISTA_CONTATOS.stream()
                .filter(criarPredicadoFiltro(filtro))
                .collect(Collectors.toList());

        logger.info("Filtro aplicado: {} contatos encontrados", contatos.size());
        return contatos;
    }

    /**
     * Busca contatos por texto (nome, telefone, email)
     */
    public List<Contato> buscarPorTexto(String texto) {
        logger.debug("Buscando contatos por texto: '{}'", texto);

        String textoLower = texto.toLowerCase();
        List<Contato> contatos = LISTA_CONTATOS.stream()
                .filter(contato
                        -> contato.getNome().toLowerCase().contains(textoLower)
                || (contato.getTelefone() != null && contato.getTelefone().contains(texto))
                || (contato.getEmail() != null && contato.getEmail().toLowerCase().contains(textoLower))
                || (contato.getEndereco() != null && contato.getEndereco().toLowerCase().contains(textoLower)))
                .collect(Collectors.toList());

        logger.info("Busca por texto '{}': {} contatos encontrados", texto, contatos.size());
        return contatos;
    }

    /**
     * Busca contatos favoritos
     */
    public List<Contato> buscarFavoritos() {
        logger.debug("Buscando contatos favoritos");
        List<Contato> favoritos = LISTA_CONTATOS.stream()
                .filter(Contato::isFavorito)
                .collect(Collectors.toList());

        logger.info("Encontrados {} contatos favoritos", favoritos.size());
        return favoritos;
    }

    /**
     * Busca contatos por categoria
     */
    public List<Contato> buscarPorCategoria(Categoria categoria) {
        logger.debug("Buscando contatos por categoria: {}", categoria);

        List<Contato> contatos = LISTA_CONTATOS.stream()
                .filter(contato -> contato.getCategoria() == categoria)
                .collect(Collectors.toList());

        logger.info("Categoria '{}': {} contatos encontrados", categoria.getDescricao(), contatos.size());
        return contatos;
    }

    /**
     * Busca contato por ID usando Optional
     */
    public Optional<Contato> buscarPorId(Long id) {
        logger.debug("Buscando contato por ID: {}", id);

        Optional<Contato> contato = LISTA_CONTATOS.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();

        if (contato.isPresent()) {
            logger.debug("Contato encontrado: {}", contato.get().getNome());
        } else {
            logger.warn("Contato com ID {} não encontrado", id);
        }

        return contato;
    }

    /**
     * Salva um novo contato
     */
    public Contato salvar(Contato contato) {
        logger.info("Salvando novo contato: {}", contato.getNome());

        // Gerar ID para novo contato
        if (contato.getId() == null) {
            Long nextId = LISTA_CONTATOS.stream()
                    .mapToLong(Contato::getId)
                    .max()
                    .orElse(0L) + 1L;
            contato.setId(nextId);
            logger.debug("ID gerado para novo contato: {}", nextId);
        }

        contato.setDataCriacao(LocalDateTime.now());
        contato.setDataAtualizacao(LocalDateTime.now());

        LISTA_CONTATOS.add(contato);
        logger.info("Contato salvo com sucesso. ID: {}, Nome: {}", contato.getId(), contato.getNome());

        return contato;
    }

    /**
     * Atualiza um contato existente
     */
    public Optional<Contato> atualizar(Long id, Contato contatoAtualizado) {
        logger.info("Atualizando contato com ID: {}", id);

        Optional<Contato> contatoExistente = buscarPorId(id);

        return contatoExistente.map(contato -> {
            logger.debug("Contato encontrado para atualização: {}", contato.getNome());

            // Atualizar campos
            contato.setNome(contatoAtualizado.getNome());
            contato.setTelefone(contatoAtualizado.getTelefone());
            contato.setEmail(contatoAtualizado.getEmail());
            contato.setEndereco(contatoAtualizado.getEndereco());
            contato.setDataNascimento(contatoAtualizado.getDataNascimento());
            contato.setCategoria(contatoAtualizado.getCategoria());
            contato.setFavorito(contatoAtualizado.isFavorito());
            contato.setDataAtualizacao(LocalDateTime.now());

            logger.info("Contato atualizado com sucesso. ID: {}, Nome: {}", id, contato.getNome());
            return contato;
        });
    }

    /**
     * Remove um contato
     */
    public boolean remover(Long id) {
        logger.info("Removendo contato com ID: {}", id);

        Optional<Contato> contatoParaRemover = buscarPorId(id);

        if (contatoParaRemover.isPresent()) {
            Contato contato = contatoParaRemover.get();
            boolean removido = LISTA_CONTATOS.remove(contato);

            if (removido) {
                logger.info("Contato removido com sucesso. ID: {}, Nome: {}", id, contato.getNome());
            } else {
                logger.error("Erro ao remover contato. ID: {}", id);
            }

            return removido;
        } else {
            logger.warn("Tentativa de remover contato inexistente. ID: {}", id);
            return false;
        }
    }

    /**
     * Marca/desmarca contato como favorito
     */
    public Optional<Contato> alternarFavorito(Long id) {
        logger.info("Alternando status de favorito para contato ID: {}", id);

        return buscarPorId(id).map(contato -> {
            boolean eraFavorito = contato.isFavorito();
            contato.setFavorito(!eraFavorito);
            contato.setDataAtualizacao(LocalDateTime.now());

            if (contato.isFavorito()) {
                logger.info("Contato marcado como favorito. ID: {}, Nome: {}", id, contato.getNome());
            } else {
                logger.info("Contato removido dos favoritos. ID: {}, Nome: {}", id, contato.getNome());
            }

            return contato;
        });
    }

    /**
     * Obtém estatísticas dos contatos
     */
    public Map<String, Object> obterEstatisticas() {
        logger.debug("Calculando estatísticas dos contatos");

        Map<String, Object> estatisticas = new HashMap<>();

        // Estatísticas básicas
        long total = LISTA_CONTATOS.size();
        long favoritos = LISTA_CONTATOS.stream().filter(Contato::isFavorito).count();

        // Média de idade
        double mediaIdade = LISTA_CONTATOS.stream()
                .filter(c -> c.getDataNascimento() != null)
                .mapToInt(Contato::getIdade)
                .average()
                .orElse(0.0);

        // Contagem por categoria
        Map<Categoria, Long> contagemPorCategoria = LISTA_CONTATOS.stream()
                .collect(Collectors.groupingBy(Contato::getCategoria, Collectors.counting()));

        estatisticas.put("total", total);
        estatisticas.put("favoritos", favoritos);
        estatisticas.put("mediaIdade", Math.round(mediaIdade * 10.0) / 10.0);
        estatisticas.put("contagemPorCategoria", contagemPorCategoria);

        logger.info("Estatísticas calculadas - Total: {}, Favoritos: {}, Média de idade: {}",
                total, favoritos, estatisticas.get("mediaIdade"));

        return estatisticas;
    }

    /**
     * Cria predicado dinâmico baseado nos filtros
     */
    private Predicate<Contato> criarPredicadoFiltro(ContatoFiltro filtro) {
        return contato -> {
            // Filtro por nome
            if (filtro.getNome().isPresent() && !filtro.getNome().get().trim().isEmpty()) {
                String nomeBusca = filtro.getNome().get().toLowerCase();
                if (!contato.getNome().toLowerCase().contains(nomeBusca)) {
                    return false;
                }
            }

            // Filtro por telefone
            if (filtro.getTelefone().isPresent() && !filtro.getTelefone().get().trim().isEmpty()) {
                String telefoneBusca = filtro.getTelefone().get();
                if (!contato.getTelefone().contains(telefoneBusca)) {
                    return false;
                }
            }

            // Filtro por email
            if (filtro.getEmail().isPresent() && !filtro.getEmail().get().trim().isEmpty()) {
                String emailBusca = filtro.getEmail().get().toLowerCase();
                if (contato.getEmail() == null || !contato.getEmail().toLowerCase().contains(emailBusca)) {
                    return false;
                }
            }

            // Filtro por categoria
            if (filtro.getCategoria().isPresent()) {
                if (contato.getCategoria() != filtro.getCategoria().get()) {
                    return false;
                }
            }

            // Filtro por favorito
            if (filtro.getFavorito().isPresent()) {
                if (contato.isFavorito() != filtro.getFavorito().get()) {
                    return false;
                }
            }

            // Filtro por data de nascimento (início)
            if (filtro.getDataNascimentoInicio().isPresent()) {
                if (contato.getDataNascimento() == null
                        || contato.getDataNascimento().isBefore(filtro.getDataNascimentoInicio().get())) {
                    return false;
                }
            }

            // Filtro por data de nascimento (fim)
            if (filtro.getDataNascimentoFim().isPresent()) {
                if (contato.getDataNascimento() == null
                        || contato.getDataNascimento().isAfter(filtro.getDataNascimentoFim().get())) {
                    return false;
                }
            }

            return true;
        };
    }

    /**
     * Obtém todas as categorias disponíveis
     */
    public List<Categoria> obterCategorias() {
        logger.debug("Obtendo lista de categorias");
        return Arrays.asList(Categoria.values());
    }
}
