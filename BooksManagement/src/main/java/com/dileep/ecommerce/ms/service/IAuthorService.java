package com.dileep.ecommerce.ms.service;

import java.util.List;

import com.dileep.ecommerce.ms.dto.AuthorDTO;

public interface IAuthorService {

	public AuthorDTO save(AuthorDTO authorDTO); 
	
	public AuthorDTO getByAuthorName(String autorName);
	
	public List<AuthorDTO> getAll();

	public AuthorDTO udpate(Long id, AuthorDTO authorDTO);

	public AuthorDTO getById(Long id);

}
