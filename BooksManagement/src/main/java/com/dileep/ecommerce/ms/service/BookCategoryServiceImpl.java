package com.dileep.ecommerce.ms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.dto.CategoryDTO;
import com.dileep.ecommerce.ms.repository.IBookCategoryRepository;

@Service
public class BookCategoryServiceImpl implements IBookCategoryService{

	@Autowired
	private IBookCategoryRepository bookCategoryRepository;
	
	@Override
	public CategoryDTO save(CategoryDTO categoryDTO) {
		try {
		 List<BookDTO> bookDTOs =	categoryDTO.getBookDTOs();
		 
//		 bookDTOs.stream()
		 
		} catch (Exception e) {

		}
		
		bookCategoryRepository.save(null);
		return null;
	}

	
	
}
