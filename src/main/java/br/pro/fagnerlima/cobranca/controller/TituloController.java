package br.pro.fagnerlima.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.pro.fagnerlima.cobranca.model.StatusTitulo;
import br.pro.fagnerlima.cobranca.model.Titulo;
import br.pro.fagnerlima.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private Titulos titulos;
	
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> titulosList = titulos.findAll();
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", titulosList);
		
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes redirectAttributes) {
		if(errors.hasErrors())
			return "CadastroTitulo";
		
		titulos.save(titulo);
		redirectAttributes.addFlashAttribute("mensagem", "Título salvo com sucesso.");
		
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		
		return mv;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		titulos.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Título excluído com sucesso.");
		
		return "redirect:/titulos";
	}
	
	@ModelAttribute("statusTituloList")
	public List<StatusTitulo> statusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
}