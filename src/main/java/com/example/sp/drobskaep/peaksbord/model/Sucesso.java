package com.example.sp.drobskaep.peaksbord.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Sucesso {
	private HttpStatus statusCode;
	private String message;
	
	
	public Sucesso(HttpStatus statusCode, String message) {
		
		this.statusCode = statusCode;
		this.message = message;
	}

	
}
