package com.dileep.ecommerce.ms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.dto.AuthorDTO;
import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.dto.CategoryDTO;
import com.dileep.ecommerce.ms.entity.BookEntity;
import com.dileep.ecommerce.ms.repository.IBookRepository;

import jakarta.persistence.AttributeConverter;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private IBookRepository bookRepository;

	@Autowired
	private IAuthorService authorService;
	
	@Autowired
	private IBookCategoryService  bookCategoryService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BookDTO save(BookDTO bookDTO) {
		try {
			AuthorDTO authorDTO = new AuthorDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			if(bookDTO.isNewauthor()) {
				authorDTO.setName(bookDTO.getAuthorName());
				authorDTO = authorService.save(authorDTO);
				bookDTO.setAuthorId(authorDTO.getId());
			}else {
				authorDTO = authorService.getByAuthorName(bookDTO.getAuthorName());
				bookDTO.setAuthorId(authorDTO.getId());
			}
			if(bookDTO.isNewCategory()) {
				categoryDTO.setName(bookDTO.getCategoryName());
				categoryDTO = bookCategoryService.save(categoryDTO);
				bookDTO.setCategoryId(categoryDTO.getId());
			}else {
				
			}
			
			
			BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);
			bookEntity = bookRepository.save(bookEntity);
			bookDTO = modelMapper.map(bookEntity, BookDTO.class);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public BookDTO udpate(Long id, BookDTO bookDTO) {
		try {
			BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);
			bookEntity = bookRepository.save(bookEntity);
			bookDTO = modelMapper.map(bookEntity, BookDTO.class);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public BookDTO getById(Long id) {
		try {
			Optional<BookEntity> optional = bookRepository.findById(id);
			BookEntity entity = optional.isPresent() ? optional.get() : null;
			BookDTO dto = new BookDTO();
			if (entity != null) {
				dto = modelMapper.map(entity, BookDTO.class);
			}
			return dto;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public List<BookDTO> getAll() {

		List<BookEntity> list = bookRepository.findAll();

		List<BookDTO> bookDTOs = list.stream().map(obj -> {
			BookDTO bookDTO = modelMapper.map(obj, BookDTO.class);
			return bookDTO;
		}).collect(Collectors.toList());
		return bookDTOs;
	}

	@Override
	public BookDTO delete(Long id) {
		try {
			BookDTO dto = getById(id);
			bookRepository.deleteById(id);
			return dto;
		} catch (Exception e) {
			return null;
		}

	}


	
	@Override
	public ResponseEntity<?> getPdf(Long id,String fileName) {

		try {
			
			BookDTO bookDTO = getById(id);
			
			if(bookDTO!=null && bookDTO.getFileName().equals(fileName)) {
				
				 byte[] pdfContent = bookDTO.getFile();
			     ByteArrayResource resource = new ByteArrayResource(pdfContent);

			     return ResponseEntity.ok()
			                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".pdf")
			                .contentType(MediaType.APPLICATION_PDF)
			                .contentLength(pdfContent.length)
			                .body(resource);
				 
			}else {
				throw new Exception("File doesn't exits");
			}
			
//			 byte[] pdfContent = ;

//		        if (pdfContent == null || pdfContent.length == 0) {
//		            return ResponseEntity.notFound().build();
//		        }

//		        ByteArrayResource resource = new ByteArrayResource(pdfContent);

//		        return ResponseEntity.ok()
//		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename + ".pdf")
//		                .contentType(MediaType.APPLICATION_PDF)
//		                .contentLength(pdfContent.length)
//		                .body(resource);
		} catch (Exception e) {
			e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
		
	}

}
