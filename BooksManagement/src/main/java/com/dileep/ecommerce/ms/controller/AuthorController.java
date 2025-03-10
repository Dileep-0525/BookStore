package com.dileep.ecommerce.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dileep.ecommerce.ms.dto.AuthorDTO;
import com.dileep.ecommerce.ms.service.IAuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private IAuthorService authorService;
	
	@PostMapping("/all")
	public List<AuthorDTO> getAll(){
		try {
			List<AuthorDTO> list = authorService.getAll();
			return list;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/save")
	public AuthorDTO save(@RequestPart String author,@RequestPart(required = false) MultipartFile photo){
		try {
			
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			AuthorDTO dto = mapper.readValue(author,AuthorDTO.class);
			if(photo!=null){
				dto.setFileType(photo.getContentType());
				dto.setPhoto(photo.getBytes());
				dto.setFileName(photo.getOriginalFilename());
			}
			 dto = authorService.save(dto);
			return dto;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/update/{id}")
	public AuthorDTO update(@PathVariable Long id,@RequestBody AuthorDTO authorDTO){
		try {
			authorDTO = authorService.udpate(id, authorDTO);
			return authorDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	@PostMapping("/getOne/{id}")
	public AuthorDTO getById(@PathVariable Long id){
		try {
			AuthorDTO authorDTO = authorService.getById(id);
			return authorDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
}
