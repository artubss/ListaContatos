package com.algaworks;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;

@Controller
public class ContatosControle {	
	
	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();
	
	static {
		LISTA_CONTATOS.add(new Contato("1", "João da Silva", "1199999-9999"));
		LISTA_CONTATOS.add(new Contato("2", "Maria Oliveira", "1299999-9999"));
		LISTA_CONTATOS.add(new Contato("3", "Pedro Santos", "1399999-9999"));
		LISTA_CONTATOS.add(new Contato("4", "Ana Souza", "1499999-9999"));
		LISTA_CONTATOS.add(new Contato("5", "Carlos Oliveira", "1599999-9999"));
		LISTA_CONTATOS.add(new Contato("6", "Rafaela Santos", "1699999-9999"));
		LISTA_CONTATOS.add(new Contato("7", "Lucas Oliveira", "1799999-9999"));
		LISTA_CONTATOS.add(new Contato("8", "Rafaela Santos", "1899999-9999"));
		LISTA_CONTATOS.add(new Contato("9", "Lucas Oliveira", "1999999-9999"));
	} 

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("listar");
		mv.addObject("contatos", LISTA_CONTATOS);
		return mv;
	}

	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("contato", new Contato());
		return mv;
	}

	@PostMapping("/contatos")
	public ModelAndView salvar(@Valid Contato contato, BindingResult result) {
		ModelAndView mv = new ModelAndView("formulario");
		if (result.hasErrors()) {
			mv.addObject("contato", contato);
			mv.addObject(
					"erros", result.getAllErrors()
			);
			return mv;
		}
		LISTA_CONTATOS.add(contato);
		mv.setViewName("redirect:/contatos");
		return mv;
	}

	@GetMapping("/contatos/{id}")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("contato", LISTA_CONTATOS.stream()
			.filter(c -> c.getId().toString().equals(id))
			.findFirst()
			.orElse(new Contato()));
		return mv;
	}

	@PostMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		Integer index = null;
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			if (LISTA_CONTATOS.get(i).getId().equals(contato.getId())) {
				index = i;
				break;
			}
		}
		if (index != null) {
			LISTA_CONTATOS.set(index, contato);
		}
		return "redirect:/contatos";
	}

	@DeleteMapping("/contatos/{id}")
	public String remover(@PathVariable String id) {
		LISTA_CONTATOS.removeIf(c -> c.getId().toString().equals(id));
		return "redirect:/contatos";
	}

	@GetMapping("/contatos/{id}/detalhes")
	public ModelAndView detalhes(@PathVariable String id) {
		ModelAndView mv = new ModelAndView("detalhes");
		mv.addObject("contato", LISTA_CONTATOS.stream()
			.filter(c -> c.getId().toString().equals(id))
			.findFirst()
			.orElse(null));
		return mv;
	}
}