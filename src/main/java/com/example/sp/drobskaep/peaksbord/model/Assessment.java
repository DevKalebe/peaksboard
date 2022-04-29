package com.example.sp.drobskaep.peaksbord.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Assessment {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private SkatePark skatePark;
	private double note;
	private String comment;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dateVisit;
	@ManyToOne
	private Usuario user;
}
