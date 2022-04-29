package com.example.sp.drobskaep.peaksbord.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sp.drobskaep.peaksbord.model.SkatePark;
import com.example.sp.drobskaep.peaksbord.repository.SkateparkRepository;

@RestController
@RequestMapping("/api/skatepark")
public class SkateparkRestController {
	
	
	@Autowired
	private SkateparkRepository repository;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<SkatePark> getSkateparks() {
		
		return repository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<SkatePark> getSkatepark(@PathVariable("id") Long idSkatepark) {
		// tenta buscar a skatepark
		Optional<SkatePark> optional = repository.findById(idSkatepark);
		// se o restaurante existir 
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@RequestMapping(value="/category/{category}", method = RequestMethod.GET)
	public Iterable<SkatePark> getSkateparkCategory(@PathVariable("category") Long categoriaSk) {
	
		return repository.findByCategorySkId(categoriaSk);
	}
	
	public ResponseEntity<Void> deleteUser(Long idUsuario) {
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}
}
