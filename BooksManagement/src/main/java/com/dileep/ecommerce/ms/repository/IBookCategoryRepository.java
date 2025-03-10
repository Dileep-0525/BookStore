package com.dileep.ecommerce.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dileep.ecommerce.ms.entity.BookCategoryEntity;

public interface IBookCategoryRepository extends JpaRepository<BookCategoryEntity, Long>{

}
