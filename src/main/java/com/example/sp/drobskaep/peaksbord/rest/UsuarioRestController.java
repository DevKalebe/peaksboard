package com.example.sp.drobskaep.peaksbord.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sp.drobskaep.peaksbord.annotation.Private;
import com.example.sp.drobskaep.peaksbord.annotation.Public;
import com.example.sp.drobskaep.peaksbord.model.Erro;
import com.example.sp.drobskaep.peaksbord.model.SkatePark;
import com.example.sp.drobskaep.peaksbord.model.Sucesso;
import com.example.sp.drobskaep.peaksbord.model.Usuario;
import com.example.sp.drobskaep.peaksbord.repository.UserRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	@Autowired
	private UserRepository repository;
	
	// salvar um usuario
	@Public
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createUser(@RequestBody Usuario usuario) {
		try {
			// insere o usuario no banco de dados
			repository.save(usuario);
			// retorna um código HTTP 201, informa como acessar o recurso inserido
			// e acrescenta no corpo da resposta o objeto inserido
			return ResponseEntity.created(URI.create("/api/usuario/"+usuario.getId())).body(usuario);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR,
					"Registro Duplicado", e.getClass().getName());
			
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage(), e.getClass().getName());
			
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
	}
	
	// listar os usuarios
	@Private
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<Usuario> getUsuarios() {
		
		return repository.findAll();
	}
	
	// buscar por ID
	@Private
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getSkatepark(@PathVariable("id") Long idUsuario) {
		// tenta buscar a skatepark
		Optional<Usuario> optional = repository.findById(idUsuario);
		// se o restaurante existir 
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id) {
		// validação do ID 
		if (id != usuario.getId()) {
			throw new RuntimeException("ID Inválido");
		}
		repository.save(usuario);
//		Sucesso s = new Sucesso(HttpStatus.,
//				s.getMessage("sd"));
		
		return ResponseEntity.ok().build();
	}
	
	@Private
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long idUsuario) {
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}
}
