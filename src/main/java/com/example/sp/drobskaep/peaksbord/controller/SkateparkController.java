package com.example.sp.drobskaep.peaksbord.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sp.drobskaep.peaksbord.model.SkatePark;
import com.example.sp.drobskaep.peaksbord.repository.CategorySkateparkRepository;
import com.example.sp.drobskaep.peaksbord.repository.SkateparkRepository;
import com.example.sp.drobskaep.peaksbord.util.FirebaseUtil;

@Controller
public class SkateparkController {

	@Autowired
	private CategorySkateparkRepository repCategory;
	@Autowired
	private SkateparkRepository repSkate;
	@Autowired
	private FirebaseUtil fireUtil;

	@RequestMapping("registerSkatepark")
	public String formSkatepark(Model model) {

		model.addAttribute("categorys", repCategory.findAllByOrderByCategoryAsc());

		return "Skatepark/formSkatepark";
	}

	// Metodo que salva no banco de dados
	@RequestMapping("saveSkatepark")
	public String saveSkatepark(SkatePark skatepark, @RequestParam("photoSk") MultipartFile[] photoSk,
			RedirectAttributes attr, BindingResult result) {

		if (result.hasErrors()) {
			attr.addFlashAttribute("messageErro", "Verifique se seus campos estão corretos");
			return "redirect:registerSkatepark";
		}
		try {

			// String para armazenar as URLs
			String fotos = skatepark.getPhoto();
			// percorre cada arquivo no vetor
			for (MultipartFile arquivo : photoSk) {
				// verifica se o arquivo existe
				if (arquivo.getOriginalFilename().isEmpty()) {
					// vai para o próximo arquivo
					continue;
				}

				try {
					fotos += fireUtil.upload(arquivo) + ";";
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

			}

			// guarda na variável Skatepark as fotos
			skatepark.setPhoto(fotos);
			// salva no BD
			repSkate.save(skatepark);
			attr.addFlashAttribute("messageSucesso", "A SkatePark " + skatepark.getNameSk() + " de ID: "
					+ skatepark.getId() + " foi cadastrada com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
			attr.addFlashAttribute("messageErro", "Houve um erro ao cadastrar " + e.getMessage());
			attr.addFlashAttribute("skatepark", skatepark);
		}

		return "redirect:registerSkatepark";
	}

	// metodo que lista as skateparks cadastradas
	@RequestMapping("listaSkatepark/{page}")
	public String listaSkateparks(Model model, @PathVariable("page") int page) {

		// cria um pageable informando os parâmetors da página
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nameSk"));
		// cria uma page de skateparks atraves do parâmetros passados pelo repositor
		Page<SkatePark> pagina = repSkate.findAll(pageable);
		// adiciona a model com as skateparks
		model.addAttribute("parks", pagina.getContent());
		// variavel para o total de paginas
		int totalPages = pagina.getTotalPages();
		// criar um list de inteiros para armazenar os numeros das paginas
		List<Integer> numPaginas = new ArrayList<Integer>();
		// preencher o list com as páginas
		for (int i = 1; i <= totalPages; i++) {
			// adiciona a página ao list
			numPaginas.add(i);
		}

		// adiciona os valores na model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalpags", totalPages);
		model.addAttribute("pagAtual", page);
		// retorna para o html da lista

		return "Skatepark/listSkatepark";
	}

	@RequestMapping("alterarSkatepark")
	public String alterarSkatepark(Long id, Model model) {

		SkatePark skatepark = repSkate.findById(id).get();
		model.addAttribute("skatepark", skatepark);
		return "redirect:listaSkatepark/1";
	}

	@RequestMapping("excluirSkatepark")
	public String excluirSkatepark(Long id) {

		SkatePark skatepark = repSkate.findById(id).get();

		if (skatepark.getPhoto().length() > 0) {
			for (String foto : skatepark.verFotos()) {
				fireUtil.deletar(foto);
			}
		}

		repSkate.deleteById(id);
		return "redirect:listaSkatepark/1";
	}

	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idSk, int numFoto, Model model) {
		// busca o Restaurante
		SkatePark skatepark = repSkate.findById(idSk).get();
		// busca a URL da foto
		String urlFoto = skatepark.verFotos()[numFoto];
		// deleta a foto
		fireUtil.deletar(urlFoto);
		// remove a url do atributo fotos
		skatepark.setPhoto(skatepark.getPhoto().replace(urlFoto + ";", ""));
		// salva no BD
		repSkate.save(skatepark);
		// coloca o rest na Model
		model.addAttribute("skatepark", skatepark);
		return "forward:registerSkatepark";
	}
}
