package com.example.sp.drobskaep.peaksbord.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Erro {
	private HttpStatus statusCode;
	private String message;
	private String exception;
	
	
	public Erro(HttpStatus statusCode, String message, String exception) {

		this.statusCode = statusCode;
		this.message = message;
		this.exception = exception;
	}
	
	
}
