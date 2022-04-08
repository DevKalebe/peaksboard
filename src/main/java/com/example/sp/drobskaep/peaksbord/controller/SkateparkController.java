package com.example.sp.drobskaep.peaksbord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sp.drobskaep.peaksbord.model.SkatePark;
import com.example.sp.drobskaep.peaksbord.repository.CategorySkateparkRepository;
import com.example.sp.drobskaep.peaksbord.repository.SkateparkRepository;

@Controller
public class SkateparkController {
	
	@Autowired
	private CategorySkateparkRepository repCategory;
	@Autowired
	private SkateparkRepository repSkate;
	
	
	@RequestMapping("registerSkatepark")
	public String formSkatepark(Model model) {
		
		model.addAttribute("categorys", repCategory.findAllByOrderByCategoryAsc());
		
		return "Skatepark/formSkatepark";
	}
	
	@RequestMapping("saveSkatepark")
	public String saveSkatepark(SkatePark skatepark, @RequestParam("photoSk") MultipartFile[] photoSk, RedirectAttributes attr, BindingResult result) {
		
		
		
		if (result.hasErrors()) {
			attr.addFlashAttribute("messageErro","Verifique se seus campos estão corretos");
			return "redirect:registerSkatepark";
		}
		try {
			
			repSkate.save(skatepark);
			attr.addFlashAttribute("messageSucesso","A SkatePark "+skatepark.getNameSk()+" de ID: "+skatepark.getId()+" foi cadastrada com sucesso");
			
			
		} catch (Exception e) {
			attr.addFlashAttribute("messageErro","Houve um erro ao cadastrar "+e.getMessage());
			attr.addFlashAttribute("skatepark", skatepark);
		}
		
		return "redirect:registerSkatepark";
	}
}
