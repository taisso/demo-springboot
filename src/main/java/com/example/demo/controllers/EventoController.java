package com.example.demo.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Convidado;
import com.example.demo.model.Evento;
import com.example.demo.repostory.ConvidadoRepository;
import com.example.demo.repostory.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;


	@RequestMapping("/")
	public String form1() {

		return "redirect:/eventos";

	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {

		return "evento/formEvento";
	}

	@Transactional
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form2(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}

		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");

		return "redirect:/cadastrarEvento";

	}

	@RequestMapping("/eventos")
	public ModelAndView listaEvento() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> evento = er.findAll();
		mv.addObject("eventos", evento);
		return mv;

	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		
		return "redirect:/eventos";
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEventos(@PathVariable("codigo") Long codigo) {

		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEventos");
		mv.addObject("evento", evento);

		Iterable<Convidado> convidado = cr.findByEvento(evento);
		mv.addObject("convidados", convidado);

		return mv;

	}
	
	@Transactional
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		
		Convidado convidado = cr.findByrg(rg);
		cr.delete(convidado);
		
		
		Evento evento = convidado.getEvento();
		long codigo = evento.getCodigo();
		String cod = String.valueOf(codigo);
		return "redirect:/" +  cod;
		
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String form3(@PathVariable("codigo") Long codigo, @Valid Convidado convidado, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}

		Evento evento = er.findByCodigo(codigo);

		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{codigo}";

	}

}
