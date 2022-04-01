package com.example.sp.drobskaep.peaksbord.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class CategorySkatepark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// validando todos os campos do Class SkatePark
	@NotEmpty(message = "O categoria da pista de skate não pode ser vazia") @Size(min= 1, max=100, message="O nome da categoria da pista tem que ser de até 100 caracters")
	@Column(name = "Categoria_Pista")
	private String category;
	
	@NotEmpty(message = "O nivel da categoria deve ser selecionado")
	private String levelCategory;
	
	@NotEmpty(message="Palavra chave não pode estar vazia")
	private String keyWord;
}
