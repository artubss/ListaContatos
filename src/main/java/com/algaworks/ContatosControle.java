package com.algaworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ContatosControle {

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/contatos")
    public ModelAndView listar(@RequestParam(required = false) String busca,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean favorito) {
        ModelAndView mv = new ModelAndView("listar");

        List<Contato> contatos;

        if (busca != null && !busca.trim().isEmpty()) {
            // Busca por texto
            contatos = contatoService.buscarPorTexto(busca);
            mv.addObject("busca", busca);
        } else if (categoria != null && !categoria.trim().isEmpty()) {
            // Busca por categoria
            Categoria cat = Categoria.fromDescricao(categoria);
            contatos = contatoService.buscarPorCategoria(cat);
            mv.addObject("categoriaSelecionada", cat);
        } else if (favorito != null && favorito) {
            // Busca favoritos
            contatos = contatoService.buscarFavoritos();
            mv.addObject("mostrarFavoritos", true);
        } else {
            // Lista todos
            contatos = contatoService.buscarTodos();
        }

        mv.addObject("contatos", contatos);
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("estatisticas", contatoService.obterEstatisticas());

        return mv;
    }

    @GetMapping("/contatos/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("contato", new Contato());
        mv.addObject("categorias", contatoService.obterCategorias());
        return mv;
    }

    @PostMapping("/contatos")
    public ModelAndView salvar(@Valid Contato contato, BindingResult result) {
        ModelAndView mv = new ModelAndView("formulario");

        if (result.hasErrors()) {
            mv.addObject("erros", result.getAllErrors());
            mv.addObject("categorias", contatoService.obterCategorias());
            return mv;
        }

        contatoService.salvar(contato);
        mv.setViewName("redirect:/contatos");
        return mv;
    }

    @GetMapping("/contatos/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("formulario");

        Optional<Contato> contatoOpt = contatoService.buscarPorId(id);
        Contato contato = contatoOpt.orElse(new Contato());

        mv.addObject("contato", contato);
        mv.addObject("categorias", contatoService.obterCategorias());
        return mv;
    }

    @PostMapping("/contatos/{id}")
    public String atualizar(@PathVariable Long id, @Valid Contato contato, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/contatos/" + id;
        }

        contatoService.atualizar(id, contato);
        return "redirect:/contatos";
    }

    @DeleteMapping("/contatos/{id}")
    public String remover(@PathVariable Long id) {
        contatoService.remover(id);
        return "redirect:/contatos";
    }

    @PostMapping("/contatos/{id}/favorito")
    public String alternarFavorito(@PathVariable Long id) {
        contatoService.alternarFavorito(id);
        return "redirect:/contatos";
    }

    @GetMapping("/contatos/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("detalhes");

        Optional<Contato> contatoOpt = contatoService.buscarPorId(id);
        mv.addObject("contato", contatoOpt.orElse(null));

        return mv;
    }

    @GetMapping("/contatos/favoritos")
    public ModelAndView favoritos() {
        ModelAndView mv = new ModelAndView("listar");
        mv.addObject("contatos", contatoService.buscarFavoritos());
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("mostrarFavoritos", true);
        mv.addObject("estatisticas", contatoService.obterEstatisticas());
        return mv;
    }

    @GetMapping("/contatos/categoria/{categoria}")
    public ModelAndView porCategoria(@PathVariable String categoria) {
        ModelAndView mv = new ModelAndView("listar");

        Categoria cat = Categoria.fromDescricao(categoria);
        List<Contato> contatos = contatoService.buscarPorCategoria(cat);

        mv.addObject("contatos", contatos);
        mv.addObject("categorias", contatoService.obterCategorias());
        mv.addObject("categoriaSelecionada", cat);
        mv.addObject("estatisticas", contatoService.obterEstatisticas());

        return mv;
    }
}
