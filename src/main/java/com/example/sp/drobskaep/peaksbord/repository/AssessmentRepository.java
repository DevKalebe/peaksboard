package com.example.sp.drobskaep.peaksbord.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.sp.drobskaep.peaksbord.model.Assessment;

public interface AssessmentRepository extends PagingAndSortingRepository<Assessment, Long>{
	
}
