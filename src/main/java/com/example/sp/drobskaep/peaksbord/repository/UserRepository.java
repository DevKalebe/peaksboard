package com.example.sp.drobskaep.peaksbord.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.sp.drobskaep.peaksbord.model.Usuario;

public interface UserRepository extends PagingAndSortingRepository<Usuario, Long>{
	public Usuario findByEmailAndPassword(String email, String password);
}
