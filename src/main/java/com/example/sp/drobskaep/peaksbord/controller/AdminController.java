package com.example.sp.drobskaep.peaksbord.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sp.drobskaep.peaksbord.model.Administrador;
import com.example.sp.drobskaep.peaksbord.repository.AdminRepository;

@Controller
public class AdminController {
	
	//variavel para percistencia dos dados
	@Autowired
	private AdminRepository repository;
	
	@RequestMapping("formRegisterAdmin")
	public String formRegisterAdmin() {
		
		return "admin/registerAdmin";
	}
	
	// Requst ,apping para salvar o admin, do tipo post
	@RequestMapping(value = "registerAdmin", method = RequestMethod.POST)
	public String registerAdimn(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		
		
		// verifica se houver erros na aplicação
		if (result.hasErrors()) {
			// adiciona uma mensagem de erro, caso estiver algo errado
			attr.addFlashAttribute("messageErro","Verifique os campos...");
		}
		
		try {
			// salva no banco de dados
			repository.save(admin);
			// adiciona uma mendagem de sucesso
			attr.addFlashAttribute("messageSucesso","Adminstrador cadastrado com sucesso. ID: "+admin.getId()+" Nome: "+admin.getName());
			
		} catch (Exception e) {
			attr.addFlashAttribute("messageErro","Houve um erro ao cadastrar"+e.getMessage());
			//mostra um erro se estiver vazio
			attr.addFlashAttribute("admin", admin);
		}
		
		return "redirect:formRegisterAdmin";
	}
}
