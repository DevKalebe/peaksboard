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
public class SkatePark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// validando todos os campos do Class SkatePark
	@NotEmpty(message = "O nome do pico não pode ser vazio") @Size(min= 1, max=100, message="O nome do pico tem que ser de até 100 caracters")
	private String name;
	@Column(name = "tipo")
	@NotEmpty(message = "Você precisa informar se a pista é pública ou privada")
	private String type;
	@NotEmpty(message ="Informe o tipo de pista")
	private String typePark;
	@NotEmpty(message="Informe todas as informações do pico")
	@Column(name = "descricao")
	private String desc;
}
