package com.example.sp.drobskaep.peaksbord.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
	@NotEmpty
	private String name;
	// define a coluna como unica
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String password;
}
