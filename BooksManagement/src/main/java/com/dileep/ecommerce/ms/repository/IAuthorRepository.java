package com.dileep.ecommerce.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dileep.ecommerce.ms.entity.AuthorEntity;

public interface IAuthorRepository extends JpaRepository<AuthorEntity, Long>{

	@Query(value ="select * from authors where name=:name",nativeQuery = true )
	public AuthorEntity getAuthorByName(String name);
	
	
}
