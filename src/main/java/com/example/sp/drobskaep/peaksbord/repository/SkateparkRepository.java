package com.example.sp.drobskaep.peaksbord.repository;



import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.sp.drobskaep.peaksbord.model.SkatePark;

public interface SkateparkRepository extends PagingAndSortingRepository<SkatePark, Long>{
		
	public List<SkatePark> findByCategorySkId(Long categorySk);
}
