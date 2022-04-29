package com.example.sp.drobskaep.peaksbord.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sp.drobskaep.peaksbord.model.Assessment;
import com.example.sp.drobskaep.peaksbord.repository.AssessmentRepository;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentRestController {
	
	@Autowired
	private AssessmentRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
		
		repository.save(assessment);
		
		return ResponseEntity.created(URI.create("/api/assessment/"+assessment.getId())).body(assessment);
	}
}
