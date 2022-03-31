package com.example.sp.drobskaep.peaksbord.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sp.drobskaep.peaksbord.model.SkatePark;
import com.example.sp.drobskaep.peaksbord.repository.SkateparkRepository;

@Controller
public class SkateParkController {
	
	// chamando minha repository
	@Autowired
	private SkateparkRepository skateparkRepository;
	
	@RequestMapping("formRegisterSkatepark")
	public String formRegisterSkatepark() {

		
		return "SkatePark/registerSkatePark";
	}
	
	@RequestMapping(value= "registerSkatepark", method = RequestMethod.POST)
	public String registerSkatepark(@Valid SkatePark skatepark, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			attr.addFlashAttribute("messageErro","Verifique se seus campos estão corretos");
			return "redirect: formRegisterSkatepark";
		}
		try {
			
			skateparkRepository.save(skatepark);
			attr.addFlashAttribute("messageSucesso","Pico de skate "+skatepark.getName()+" cadastrado com sucesso, número de ID: "+skatepark.getId());
			
			
		} catch (Exception e) {
			attr.addFlashAttribute("messageErro","Houve um erro ao cadastrar "+e.getMessage());
			attr.addFlashAttribute("skatepark", skatepark);
		}
		return "redirect:formRegisterSkatepark";
	}
}
