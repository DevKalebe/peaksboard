package com.example.sp.drobskaep.peaksbord.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sp.drobskaep.peaksbord.annotation.Private;
import com.example.sp.drobskaep.peaksbord.model.CategorySkatepark;
import com.example.sp.drobskaep.peaksbord.repository.CategorySkateparkRepository;

@Controller
public class CaterogySkateparkController {
	
	// Chamando a Repository das category
	@Autowired
	private CategorySkateparkRepository categoryRepository;
	
	// Metodo Get para o formulario de registro de categorias
	@Private
	@RequestMapping("FormRegisterCategory")
	public String formRegisterSkatepark() {

		
		return "CategorySkatePark/registerCategorySkatepark";
	}
	
	// Metodo para salvar a categoria
	@Private
	@RequestMapping(value= "registerCategory", method = RequestMethod.POST)
	public String registerSkatepark(@Valid CategorySkatepark categorySkatepark, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			attr.addFlashAttribute("messageErro","Verifique se seus campos estão corretos");
			return "redirect:FormRegisterCategory";
		}
		try {
			
			categoryRepository.save(categorySkatepark);
			attr.addFlashAttribute("messageSucesso","Categoria de pico de skate "+categorySkatepark.getCategory()+" cadastrada com sucesso, número de ID: "+categorySkatepark.getId());
			
			
		} catch (Exception e) {
			attr.addFlashAttribute("messageErro","Houve um erro ao cadastrar "+e.getMessage());
			attr.addFlashAttribute("categorySkatepark", categorySkatepark);
		}
		return "redirect:FormRegisterCategory";
	}

	// Metodo para listar as categorys
	@Private
	@RequestMapping("listCategory/{page}")
	public String listCategory(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "category"));
		Page<CategorySkatepark> pagina = categoryRepository.findAll(pageable);
		model.addAttribute("categorySkatepark", pagina.getContent());
		int totalPags = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		
		for(int i = 1; i <= totalPags; i++) {
			numPaginas.add(i);
		}
		
		// Add values in Model
		model.addAttribute("numPaginas",numPaginas);
		model.addAttribute("totalPags", totalPags);
		model.addAttribute("pagAtual", page);
		return "CategorySkatepark/ListCategorySkatepark";
	}
	
	// metodo para alterar as categorias
	@Private
	@RequestMapping("changeCategory")
	public String alterCategory(Long id, Model model) {
		CategorySkatepark categorySkatepark = categoryRepository.findById(id).get();
		model.addAttribute("categorySkatepark", categorySkatepark);
		
		return "forward:FormRegisterCategory";
	}
	
	// metodo para deletar as categorias
	@Private
	@RequestMapping("deleteCategory")
	public String deletecategory(Long id) {
		
		categoryRepository.deleteById(id);
		return "redirect:listCategory/1";
	}
	
	// metodo para buscar as categorias
	@Private
	@RequestMapping("listSearch")
	public String searchCategory(Model model, String searchCategory, String choiceCategory) {
		
		if (choiceCategory.equals("all")) {
			model.addAttribute("categorySkatepark", categoryRepository.findEverything(searchCategory));
			
		} else if (choiceCategory.equals("categorys")) {
			model.addAttribute("categorySkatepark",categoryRepository.findCategory(searchCategory));
		} else if (choiceCategory.equals("levels")) {
			model.addAttribute("categorySkatepark", categoryRepository.findLevelCategory(searchCategory));	
		} else if (choiceCategory.equals("keyWords")) {
			model.addAttribute("categorySkatepark", categoryRepository.findKeyWord(searchCategory));
		}
		
		return "CategorySkatepark/ListCategorySkatepark";
	}
}

	
	