package com.dileep.ecommerce.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dileep.ecommerce.ms.entity.BookEntity;

public interface IBookRepository extends JpaRepository<BookEntity, Long>{

	@Query(value = "select * from books where id=:id and filename=:filename",nativeQuery = true)
	public BookEntity getByfileName(Long id,String filename);
	
}
