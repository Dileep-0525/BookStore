package com.dileep.ecommerce.ms.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.dileep.ecommerce.ms.dto.BookDTO;

public interface IBookService {

	public BookDTO save(BookDTO bookDTO);
	
	public BookDTO udpate(Long id,BookDTO bookDTO);
	
	public BookDTO getById(Long id);
	
	public List<BookDTO> getAll();
	
	public BookDTO delete(Long id);

	public ResponseEntity<?> getPdf(Long id,String fileName);
	
}
