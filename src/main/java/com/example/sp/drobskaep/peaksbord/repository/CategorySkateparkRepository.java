package com.example.sp.drobskaep.peaksbord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.sp.drobskaep.peaksbord.model.CategorySkatepark;

public interface CategorySkateparkRepository extends PagingAndSortingRepository<CategorySkatepark, Long>{
	
	public List<CategorySkatepark> findAllByOrderByCategoryAsc();
	
	@Query("SELECT ck FROM CategorySkatepark ck WHERE ck.category LIKE %:all% OR ck.levelCategory LIKE %:all% OR ck.keyWord LIKE %:all%")
	public List<CategorySkatepark> findEverything(@Param("all") String all);
	
	// buscar pela categoria
	@Query("SELECT ck FROM CategorySkatepark ck WHERE ck.category LIKE %:cate%")
	public List<CategorySkatepark> findCategory(@Param("cate") String cate);
	
	@Query("SELECT ck FROM CategorySkatepark ck WHERE ck.levelCategory LIKE %:level%")
	public List<CategorySkatepark> findLevelCategory(@Param("level") String level);
	
	@Query("SELECT ck FROM CategorySkatepark ck WHERE ck.keyWord LIKE %:keyword%")
	public List<CategorySkatepark> findKeyWord(@Param("keyword") String keyword);
}
