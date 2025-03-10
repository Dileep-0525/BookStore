package com.dileep.ecommerce.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.service.IBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private IBookService bookService;
	
	
	@PostMapping("/save")
	public BookDTO save(@RequestPart("book") String book,
			@RequestPart(required = false) MultipartFile file ){
		try {
			
			ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
			BookDTO bookDTO = objectMapper.readValue(book, BookDTO.class);
			
			if(file!=null) {
				bookDTO.setFileName(file.getOriginalFilename());
				bookDTO.setFileType(file.getContentType());
				bookDTO.setFile(file.getBytes());
			}
			bookDTO= bookService.save(bookDTO);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/update/{id}")
	public BookDTO update(@PathVariable Long id,@RequestBody BookDTO bookDTO){
		try {
			bookDTO = bookService.udpate(id, bookDTO);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/getOne/{id}")
	public BookDTO getById(@PathVariable Long id){
		try {
			BookDTO bookDTO = bookService.getById(id);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/all")
	public List<BookDTO> getAll(){
		try {
			List<BookDTO> list =bookService.getAll();
			return list;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/deleteOne/{id}")
	public BookDTO delete(@PathVariable Long id){
		try {
			BookDTO bookDTO = bookService.delete(id);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@PostMapping("/download/{id}/{fileName}")
	public ResponseEntity<?> getPdfByFileName(@PathVariable Long id ,@PathVariable String fileName ){
		try {
			ResponseEntity<?> response = bookService.getPdf(id,fileName);
			return response;
		} catch (Exception e) {
			e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}
	
	
}
