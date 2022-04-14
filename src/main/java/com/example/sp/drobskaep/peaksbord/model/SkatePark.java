package com.example.sp.drobskaep.peaksbord.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Data;

@Entity
@Data
public class SkatePark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nameSk;
	private String spaceSk;
	private String timeSk;
	private String cepSk;
	private String addressSk;
	private String citySk;
	private String stateSk;
	private String complementSk;
	private String levelSk;
	private String keyWordSk;
	private String materialSk;
	private String accessSk;
	@ManyToOne
	private CategorySkatepark categorySk;
	@Column(columnDefinition = "TEXT")
	private String descSk;
	@Column(columnDefinition = "TEXT")
	private String photo;
	
	// retorna as fotos na forma de vetor de String 
	public String[] verFotos() {
		return photo.split(";");
	}
}
