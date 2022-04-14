package com.example.sp.drobskaep.peaksbord.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.example.sp.drobskaep.peaksbord.model.Administrador;
import com.example.sp.drobskaep.peaksbord.repository.AdminRepository;
import com.example.sp.drobskaep.peaksbord.util.HashUtil;

@Controller
public class AdminController {
	
	//variavel para percistencia dos dados
	@Autowired
	private AdminRepository adminRepository;
	
	@RequestMapping("formRegisterAdmin")
	public String formRegisterAdmin() {
		
		return "Admin/registerAdmin";
	}
	
	// Requst ,apping para salvar o admin, do tipo post
	@RequestMapping(value = "registerAdmin", method = RequestMethod.POST)
	public String registerAdimn(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		
		
		
		// verifica se houver erros na aplicação	
		if (result.hasErrors()) {
			// adiciona uma mensagem de erro, caso estiver algo errado
			attr.addFlashAttribute("messageErro","Verifique os campos...");
			return "redirect:formRegisterAdmin";
		}
		
		// Validação de back para senha 
		int senhaValidacao = admin.getPassword().length();
		
		// variavel para descobrir alteração ou inserção
		boolean alteracao = admin.getId() != null ? true : false;
		
		if (!alteracao) {
			if (senhaValidacao < 6 || senhaValidacao > 20) {
				attr.addFlashAttribute("messageErro","A senha não pode ser menor que 6 e nem maior que 20");
				return "redirect:formRegisterAdmin";
				
			} 
		}
		
		// gera hash
		admin.setPassword(HashUtil.hash(admin.getPassword()));
		
		// verificar se a senha está vazia, se estiver vazia vai ser gerada uma senha que pegara
		// os caracters que estão antes do "@", e vai ser gerado uma senha
		if (admin.getPassword().equals(HashUtil.hash(""))) {
			if (!alteracao) {
				// retira a parte antes do @
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// "setar" a parte na senha do admin
				admin.setPassword(parte);
				
			}else {
				// nusca a senha atual no banco
				String hash = adminRepository.findById(admin.getId()).get().getPassword();
				
				
				// "setar" o hash na senha
				admin.setSenhaComHash(hash);
			}
			
		}
		
		try {
			
			// salva no banco de dados
			adminRepository.save(admin);
			// adiciona uma mendagem de sucesso
			attr.addFlashAttribute("messageSucesso","Adminstrador cadastrado com sucesso. ID: "+admin.getId()+"| Nome: "+admin.getName()+ "\nCaso Úsuario não foi informado a senha é o endereço de e-mail antes do '@'");
			
		} catch (Exception e) {
			attr.addFlashAttribute("messageErro","Houve um erro ao cadastrar"+e.getMessage());
			//mostra um erro se estiver vazio
			attr.addFlashAttribute("admin", admin);
		}
		
		return "redirect:formRegisterAdmin";
	}
		
	//Request para lista de admin
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		// cria um pageable informando os parâmetros da página
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "name"));
		// cria um page de adminstrador atraves dos parametros passador pelo repositor 
		Page<Administrador> pagina = adminRepository.findAll(pageable);
		// adciona a Model com os admin
		model.addAttribute("admins", pagina.getContent());
		// variavel para o total de paginas 
		int totalPages = pagina.getTotalPages();
		// criar um list de inteiros para armazenar os numeros das paginas 
		List<Integer> numPaginas = new ArrayList<Integer>();
		// preencher o list com as páginas
		for(int i = 1; i <= totalPages; i++) {
			// adiciona a página ao list
			numPaginas.add(i);		}
		// adiciona os valores na model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		// retorna para o html da lista
		return "Admin/listAdmin";
	}
	
	@RequestMapping("alterarAdmin")
	public String alterarAdmin(Long id, Model model) {
		Administrador administrador = adminRepository.findById(id).get();
		model.addAttribute("admin",administrador);
		
		return "forward:formRegisterAdmin";
	}
	
	@RequestMapping("excluirAdmin")
	private String excluirAdmin(Long id) {
		
		adminRepository.deleteById(id);
		return "redirect:listaAdmin/1";
	}
}

