package com.algaworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ContatoRestController {
    private static final Logger logger = LoggerFactory.getLogger(ContatoRestController.class);

    @Autowired
    private ContatoService contatoService;

    // GET /api/contatos - Listar todos os contatos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Contato>>> listarTodos() {
        logger.info("API: Listando todos os contatos");
        try {
            List<Contato> contatos = contatoService.buscarTodos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Contatos listados com sucesso", contatos));
        } catch (Exception e) {
            logger.error("Erro ao listar contatos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/contatos/{id} - Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contato>> buscarPorId(@PathVariable Long id) {
        logger.info("API: Buscando contato por ID: {}", id);
        try {
            Optional<Contato> contato = contatoService.buscarPorId(id);
            if (contato.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Contato encontrado", contato.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Contato não encontrado", null));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar contato ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // POST /api/contatos - Criar novo contato
    @PostMapping
    public ResponseEntity<ApiResponse<Contato>> criar(@Valid @RequestBody Contato contato, BindingResult result) {
        logger.info("API: Criando novo contato: {}", contato.getNome());
        
        if (result.hasErrors()) {
            logger.warn("Erros de validação na criação: {}", result.getAllErrors());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Dados inválidos", null));
        }

        try {
            Contato contatoSalvo = contatoService.salvar(contato);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Contato criado com sucesso", contatoSalvo));
        } catch (Exception e) {
            logger.error("Erro ao criar contato: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // PUT /api/contatos/{id} - Atualizar contato
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Contato>> atualizar(@PathVariable Long id, 
                                                         @Valid @RequestBody Contato contato, 
                                                         BindingResult result) {
        logger.info("API: Atualizando contato ID: {}", id);
        
        if (result.hasErrors()) {
            logger.warn("Erros de validação na atualização: {}", result.getAllErrors());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Dados inválidos", null));
        }

        try {
            Optional<Contato> contatoAtualizado = contatoService.atualizar(id, contato);
            if (contatoAtualizado.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Contato atualizado com sucesso", contatoAtualizado.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Contato não encontrado", null));
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar contato ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // DELETE /api/contatos/{id} - Remover contato
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> remover(@PathVariable Long id) {
        logger.info("API: Removendo contato ID: {}", id);
        try {
            boolean removido = contatoService.remover(id);
            if (removido) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Contato removido com sucesso", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Contato não encontrado", null));
            }
        } catch (Exception e) {
            logger.error("Erro ao remover contato ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // POST /api/contatos/{id}/favorito - Alternar favorito
    @PostMapping("/{id}/favorito")
    public ResponseEntity<ApiResponse<Contato>> alternarFavorito(@PathVariable Long id) {
        logger.info("API: Alternando favorito para contato ID: {}", id);
        try {
            Optional<Contato> contato = contatoService.alternarFavorito(id);
            if (contato.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Status de favorito alterado", contato.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Contato não encontrado", null));
            }
        } catch (Exception e) {
            logger.error("Erro ao alternar favorito do contato ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/contatos/busca?q=texto - Buscar por texto
    @GetMapping("/busca")
    public ResponseEntity<ApiResponse<List<Contato>>> buscarPorTexto(@RequestParam String q) {
        logger.info("API: Buscando contatos por texto: '{}'", q);
        try {
            List<Contato> contatos = contatoService.buscarPorTexto(q);
            return ResponseEntity.ok(new ApiResponse<>(true, "Busca realizada com sucesso", contatos));
        } catch (Exception e) {
            logger.error("Erro na busca por texto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/contatos/favoritos - Listar favoritos
    @GetMapping("/favoritos")
    public ResponseEntity<ApiResponse<List<Contato>>> listarFavoritos() {
        logger.info("API: Listando contatos favoritos");
        try {
            List<Contato> favoritos = contatoService.buscarFavoritos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Favoritos listados com sucesso", favoritos));
        } catch (Exception e) {
            logger.error("Erro ao listar favoritos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/contatos/categoria/{categoria} - Listar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<List<Contato>>> listarPorCategoria(@PathVariable String categoria) {
        logger.info("API: Listando contatos por categoria: {}", categoria);
        try {
            Categoria cat = Categoria.fromDescricao(categoria);
            List<Contato> contatos = contatoService.buscarPorCategoria(cat);
            return ResponseEntity.ok(new ApiResponse<>(true, "Contatos da categoria listados", contatos));
        } catch (Exception e) {
            logger.error("Erro ao listar por categoria: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/contatos/estatisticas - Obter estatísticas
    @GetMapping("/estatisticas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obterEstatisticas() {
        logger.info("API: Obtendo estatísticas");
        try {
            Map<String, Object> estatisticas = contatoService.obterEstatisticas();
            return ResponseEntity.ok(new ApiResponse<>(true, "Estatísticas obtidas com sucesso", estatisticas));
        } catch (Exception e) {
            logger.error("Erro ao obter estatísticas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // GET /api/categorias - Listar categorias
    @GetMapping("/categorias")
    public ResponseEntity<ApiResponse<List<Categoria>>> listarCategorias() {
        logger.info("API: Listando categorias");
        try {
            List<Categoria> categorias = contatoService.obterCategorias();
            return ResponseEntity.ok(new ApiResponse<>(true, "Categorias listadas com sucesso", categorias));
        } catch (Exception e) {
            logger.error("Erro ao listar categorias: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // POST /api/contatos/filtros - Buscar com filtros avançados
    @PostMapping("/filtros")
    public ResponseEntity<ApiResponse<List<Contato>>> buscarComFiltros(@RequestBody ContatoFiltro filtro) {
        logger.info("API: Buscando contatos com filtros: {}", filtro);
        try {
            List<Contato> contatos = contatoService.buscarComFiltros(filtro);
            return ResponseEntity.ok(new ApiResponse<>(true, "Filtro aplicado com sucesso", contatos));
        } catch (Exception e) {
            logger.error("Erro ao aplicar filtros: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Erro interno do servidor", null));
        }
    }

    // Classe interna para padronizar respostas da API
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private long timestamp;

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }

        // Getters e Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
        
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}