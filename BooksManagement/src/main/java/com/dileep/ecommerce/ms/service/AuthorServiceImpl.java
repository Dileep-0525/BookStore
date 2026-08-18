package com.dileep.ecommerce.ms.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.dto.AuthorDTO;
import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.entity.AuthorEntity;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.exceptions.NoDataFoundException;
import com.dileep.ecommerce.ms.repository.IAuthorRepository;

@Service
public class AuthorServiceImpl implements IAuthorService {

	@Autowired
	private IAuthorRepository authorRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AuthorDTO> getAll() {
		List<AuthorDTO> authorDTOs = new ArrayList<>();
		try {
			List<AuthorEntity> authorEntities = authorRepository.findAll();

			authorDTOs = authorEntities.stream().map(author -> {

				List<BookDTO> bookDTOs = Optional.ofNullable(author.getBooks()).orElse(Collections.emptyList()).stream()
						.map(book -> modelMapper.map(book, BookDTO.class)).toList();
				
//				byte[] photo = author.getPhoto();
				author.setPhoto(null);
				AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);
				authorDTO.setBookDTOs(bookDTOs);
				return authorDTO;
			}).toList();
			return authorDTOs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;//.getMessage();
//			return e.getMessage();
		}
	}

	@Override
	public AuthorDTO save(AuthorDTO authorDTO) {

		AuthorDTO dto = new AuthorDTO();
		try {
			modelMapper.typeMap(AuthorDTO.class, AuthorEntity.class).addMapping(src -> src.getBookDTOs(),
					AuthorEntity::setBooks);
			AuthorEntity authorEntity = modelMapper.map(authorDTO, AuthorEntity.class);
			authorEntity = authorRepository.save(authorEntity);

//			modelMapper.typeMap(AuthorEntity.class, AuthorDTO.class) 

			dto = getDTOFromEntity(authorEntity);
		} catch (Exception e) {
			e.getMessage();
		}
		return dto;
	}

	@Override
	public AuthorDTO getByAuthorName(String authorName) {

		AuthorDTO authorDTO = new AuthorDTO();
		try {
			AuthorEntity authorEntity = authorRepository.getAuthorByName(authorName);
			authorDTO = getDTOFromEntity(authorEntity);
		} catch (Exception e) {
			e.getMessage();
		}
		return authorDTO;
	}

	public AuthorDTO getById(Long authorId) {
		AuthorDTO authorDTO = new AuthorDTO();
		try {
			Optional<AuthorEntity> optional = authorRepository.findById(authorId);
			if (optional.isPresent()) {
				AuthorEntity entity = optional.get();
				authorDTO = getDTOFromEntity(entity);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return authorDTO;
	}

	private AuthorDTO getDTOFromEntity(AuthorEntity authorEntity) {
		AuthorDTO authorDTO = new AuthorDTO();
		try {

			modelMapper.typeMap(AuthorEntity.class, AuthorDTO.class).addMapping(src -> src.getBooks(),
					AuthorDTO::setBookDTOs);
			authorDTO = modelMapper.map(authorEntity, AuthorDTO.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse(authorDTO.getDateOfBirth().toString(), formatter);
			authorDTO.setDateOfBirth(date);
		} catch (Exception e) {
			e.getMessage();
		}
		return authorDTO;
	}

	@Override
	public AuthorDTO udpate(Long id, AuthorDTO authorDTO) {
		try {
			AuthorEntity authorEntity = authorRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("author not exists with given id"));

			modelMapper.map(authorDTO, authorEntity);
			authorEntity = authorRepository.save(authorEntity);
			return getDTOFromEntity(authorEntity);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

}
