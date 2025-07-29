package com.algaworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Controller
public class ContatosControle {

    private static final Logger logger = LoggerFactory.getLogger(ContatosControle.class);

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/")
    public String index() {
        logger.debug("Acessando página inicial");
        return "index";
    }

    @GetMapping("/contatos")
    public ModelAndView listar(@RequestParam(required = false) String busca,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean favorito) {
        logger.info("Listando contatos - Busca: {}, Categoria: {}, Favorito: {}", busca, categoria, favorito);

        ModelAndView mv = new ModelAndView("listar");

        List<Contato> contatos;

        if (busca != null && !busca.trim().isEmpty()) {
            // Busca por texto
            contatos = contatoService.buscarPorTexto(busca);
            mv.addObject("busca", busca);
            logger.debug("Busca por texto aplicada: '{}'", busca);
        } else if (categoria != null && !categoria.trim().isEmpty()) {
            // Busca por categoria
            Categoria cat = Categoria.fromDescricao(categoria);
            contatos = contatoService.buscarPorCategoria(cat);
            mv.addObject("categoriaSelecionada", cat);
            logger.debug("Filtro por categoria aplicado: {}", cat);
        } else if (favorito != null && favorito) {
            // Busca favoritos
            contatos = contatoService.buscarFavoritos();
            mv.addObject("mostrarFavoritos", true);
            logger.debug("Filtro por favoritos aplicado");
        } else {
            // Lista todos
            contatos = contatoService.buscarTodos();
            logger.debug("Listando todos os contatos");
        }

        mv.addObject("contatos", contatos);
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("estatisticas", contatoService.obterEstatisticas());

        logger.info("Retornando {} contatos para listagem", contatos.size());
        return mv;
    }

    @GetMapping("/contatos/novo")
    public ModelAndView novo() {
        logger.info("Acessando formulário de novo contato");
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("contato", new Contato());
        mv.addObject("categorias", contatoService.obterCategorias());
        return mv;
    }

    @PostMapping("/contatos")
    public ModelAndView salvar(@Valid Contato contato, BindingResult result) {
        logger.info("Tentativa de salvar novo contato: {}", contato.getNome());

        ModelAndView mv = new ModelAndView("formulario");

        if (result.hasErrors()) {
            logger.warn("Erros de validação encontrados: {}", result.getAllErrors());
            mv.addObject("erros", result.getAllErrors());
            mv.addObject("categorias", contatoService.obterCategorias());
            return mv;
        }

        try {
            contatoService.salvar(contato);
            logger.info("Contato salvo com sucesso: {}", contato.getNome());
            mv.setViewName("redirect:/contatos");
        } catch (Exception e) {
            logger.error("Erro ao salvar contato: {}", e.getMessage(), e);
            mv.addObject("erro", "Erro ao salvar contato: " + e.getMessage());
            mv.addObject("categorias", contatoService.obterCategorias());
        }

        return mv;
    }

    @GetMapping("/contatos/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        logger.info("Acessando formulário de edição para contato ID: {}", id);

        ModelAndView mv = new ModelAndView("formulario");

        Optional<Contato> contatoOpt = contatoService.buscarPorId(id);
        if (contatoOpt.isPresent()) {
            Contato contato = contatoOpt.get();
            mv.addObject("contato", contato);
            mv.addObject("categorias", contatoService.obterCategorias());
            logger.debug("Contato encontrado para edição: {}", contato.getNome());
        } else {
            logger.warn("Contato não encontrado para edição. ID: {}", id);
            mv.addObject("erro", "Contato não encontrado");
            mv.addObject("contato", new Contato());
            mv.addObject("categorias", contatoService.obterCategorias());
        }

        return mv;
    }

    @PostMapping("/contatos/{id}")
    public String atualizar(@PathVariable Long id, @Valid Contato contato, BindingResult result) {
        logger.info("Tentativa de atualizar contato ID: {}", id);

        if (result.hasErrors()) {
            logger.warn("Erros de validação na atualização: {}", result.getAllErrors());
            return "redirect:/contatos/" + id;
        }

        try {
            Optional<Contato> contatoAtualizado = contatoService.atualizar(id, contato);
            if (contatoAtualizado.isPresent()) {
                logger.info("Contato atualizado com sucesso. ID: {}", id);
            } else {
                logger.warn("Contato não encontrado para atualização. ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar contato ID {}: {}", id, e.getMessage(), e);
        }

        return "redirect:/contatos";
    }

    @DeleteMapping("/contatos/{id}")
    public String remover(@PathVariable Long id) {
        logger.info("Tentativa de remover contato ID: {}", id);

        try {
            boolean removido = contatoService.remover(id);
            if (removido) {
                logger.info("Contato removido com sucesso. ID: {}", id);
            } else {
                logger.warn("Contato não encontrado para remoção. ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Erro ao remover contato ID {}: {}", id, e.getMessage(), e);
        }

        return "redirect:/contatos";
    }

    @PostMapping("/contatos/{id}/favorito")
    public String alternarFavorito(@PathVariable Long id) {
        logger.info("Tentativa de alternar favorito para contato ID: {}", id);

        try {
            Optional<Contato> contato = contatoService.alternarFavorito(id);
            if (contato.isPresent()) {
                logger.info("Status de favorito alterado para contato ID: {}", id);
            } else {
                logger.warn("Contato não encontrado para alternar favorito. ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Erro ao alternar favorito do contato ID {}: {}", id, e.getMessage(), e);
        }

        return "redirect:/contatos";
    }

    @GetMapping("/contatos/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable Long id) {
        logger.info("Acessando detalhes do contato ID: {}", id);

        ModelAndView mv = new ModelAndView("detalhes");

        Optional<Contato> contatoOpt = contatoService.buscarPorId(id);
        if (contatoOpt.isPresent()) {
            mv.addObject("contato", contatoOpt.get());
            logger.debug("Detalhes do contato carregados: {}", contatoOpt.get().getNome());
        } else {
            logger.warn("Contato não encontrado para detalhes. ID: {}", id);
            mv.addObject("contato", null);
        }

        return mv;
    }

    @GetMapping("/contatos/favoritos")
    public ModelAndView favoritos() {
        logger.info("Acessando lista de contatos favoritos");

        ModelAndView mv = new ModelAndView("listar");
        List<Contato> favoritos = contatoService.buscarFavoritos();

        mv.addObject("contatos", favoritos);
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("mostrarFavoritos", true);
        mv.addObject("estatisticas", contatoService.obterEstatisticas());

        logger.info("Retornando {} contatos favoritos", favoritos.size());
        return mv;
    }

    @GetMapping("/contatos/categoria/{categoria}")
    public ModelAndView porCategoria(@PathVariable String categoria) {
        logger.info("Acessando contatos por categoria: {}", categoria);

        ModelAndView mv = new ModelAndView("listar");

        Categoria cat = Categoria.fromDescricao(categoria);
        List<Contato> contatos = contatoService.buscarPorCategoria(cat);

        mv.addObject("contatos", contatos);
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("categoriaSelecionada", cat);
        mv.addObject("estatisticas", contatoService.obterEstatisticas());

        logger.info("Retornando {} contatos da categoria {}", contatos.size(), cat.getDescricao());
        return mv;
    }
}
