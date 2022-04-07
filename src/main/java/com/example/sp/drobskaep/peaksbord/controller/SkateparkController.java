package com.example.sp.drobskaep.peaksbord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sp.drobskaep.peaksbord.repository.CategorySkateparkRepository;

@Controller
public class SkateparkController {
	
	@Autowired
	private CategorySkateparkRepository repCategory;
	
	@RequestMapping("registerSkatepark")
	public String formSkatepark(Model model) {
		
		model.addAttribute("categorys", repCategory.findAllByOrderByCategoryAsc());
		
		return "Skatepark/formSkatepark";
	}
}
