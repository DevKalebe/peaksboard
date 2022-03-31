package com.example.sp.drobskaep.peaksbord.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.sp.drobskaep.peaksbord.util.HashUtil;

import lombok.Data;

// cria ps getters e setters
@Data
// mapea a entidade para o jpa
@Entity
public class Administrador {
	// chave primaria e auto-inclement
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//validando campos
	@NotEmpty(message="O campo nome está vazio")
	@Size(min=2, max=26, message="Insira seu nome e sobrenome")
	private String name;
	// define a coluna como unica
	@Column(unique = true)
	@Email
	//validando campos
	@NotEmpty(message="O campo email está vazio")
	@Size(min = 10, max = 256, message="Insira um email válido")
	private String email;
	//validando campos
	@NotEmpty(message="O campo de senha está vazio")
	private String password;
	
	// se caso não precisase de uma validação do back setava o hash aqui 
//	public void setPassword(String senha) {
//		this.password = HashUtil.hash(password);
//	}
	
	public void setSenhaComHash(String hash) {
		this.password = hash;
	}
}
