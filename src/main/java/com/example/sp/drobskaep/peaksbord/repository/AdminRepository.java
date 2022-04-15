package com.example.sp.drobskaep.peaksbord.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.sp.drobskaep.peaksbord.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	
	public Administrador findByEmailandPassword(String email, String password);
}
