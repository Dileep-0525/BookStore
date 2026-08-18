package com.dileep.ecommerce.ms.service;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.ai.service.ImageEmbeddingService;
import com.dileep.ecommerce.ms.dto.AuthorDTO;
import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.dto.CategoryDTO;
import com.dileep.ecommerce.ms.entity.BookEntity;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.repository.IBookRepository;
import com.dileep.ecommerce.ms.util.PdfThumbnailGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

	private final IBookRepository bookRepository;

	private final IAuthorService authorService;

	private final PdfThumbnailGenerator pdfThumbnailGenerator;

	private final IBookCategoryService bookCategoryService;

	private final ModelMapper modelMapper;

	private final ThumbnailService thumbnailService;

	private final ImageEmbeddingService imageEmbeddingService;

	private final IPdfService pdfService;

	@Override
	public BookDTO save(BookDTO bookDTO) {
		try {
			AuthorDTO authorDTO = new AuthorDTO();
			CategoryDTO categoryDTO = new CategoryDTO();
			if (bookDTO.isNewauthor()) {
				authorDTO.setName(bookDTO.getAuthorName());
				authorDTO = authorService.save(authorDTO);
				bookDTO.setAuthorId(authorDTO.getId());
			} else {
				authorDTO = authorService.getByAuthorName(bookDTO.getAuthorName());
				bookDTO.setAuthorId(authorDTO.getId());
			}
			if (bookDTO.isNewCategory()) {
				categoryDTO.setName(bookDTO.getCategoryName());
				categoryDTO = bookCategoryService.save(categoryDTO);
				bookDTO.setCategoryId(categoryDTO.getId());
			} else {

			}

			BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);
			// Generate thumbnail only if PDF is available
			if (bookDTO.getFile() != null && bookDTO.getFile().length > 0) {
				BufferedImage cover = pdfService.extractFirstPage(bookDTO.getFile());

				byte[] thumbnail = thumbnailService.createThumbnail(cover);

				float[] embedding = imageEmbeddingService.generateEmbedding(cover);

				bookEntity.setThumbnail(thumbnail);

				// Next lesson
				bookEntity.setCoverEmbedding(embedding);

//				bookRepository.save(bookEntity);
//				repository.save(book);
				
			}

			bookEntity = bookRepository.save(bookEntity);

			bookDTO = modelMapper.map(bookEntity, BookDTO.class);

			return bookDTO;
//			
//			bookEntity = bookRepository.save(bookEntity);
//			bookDTO = modelMapper.map(bookEntity, BookDTO.class);
//			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public BookDTO update(Long id, BookDTO bookDTO) {
		try {
			BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);

			// Generate thumbnail only if PDF is available
			if (bookDTO.getFile() != null && bookDTO.getFile().length > 0) {
//				byte[] thumbnail = pdfThumbnailGenerator.generateThumbnail(bookDTO.getFile());
//				bookEntity.setThumbnail(thumbnail);
				BufferedImage cover = pdfService.extractFirstPage(bookDTO.getFile());

				byte[] thumbnail = thumbnailService.createThumbnail(cover);

				float[] embedding = imageEmbeddingService.generateEmbedding(cover);

				bookEntity.setThumbnail(thumbnail);

				// Next lesson
				bookEntity.setCoverEmbedding(embedding);
			}

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
//			String paragraph = getText(id, 16, 17);
//			System.out.println(paragraph);
			BookDTO dto = new BookDTO();
			if (entity != null) {
				dto = modelMapper.map(entity, BookDTO.class);
				dto.setFile(null);
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

		List<BookDTO> bookDTOs = (list.size() != 0) ? list.stream().map(obj -> {
			BookDTO bookDTO = modelMapper.map(obj, BookDTO.class);
			return bookDTO;
		}).collect(Collectors.toList()) : new ArrayList<>();
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
	public ResponseEntity<?> getPdf(Long id, String fileName) {

		try {

			BookDTO bookDTO = getById(id);

			if (bookDTO != null && bookDTO.getFileName().equals(fileName)) {

				byte[] pdfContent = bookDTO.getFile();
				ByteArrayResource resource = new ByteArrayResource(pdfContent);

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".pdf")
						.contentType(MediaType.APPLICATION_PDF).contentLength(pdfContent.length).body(resource);

			} else {
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

	@Override
	public String getText(Long bookId, Integer startPage, Integer endPage) {
		String content = null;
		try {
			BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new GlobalException());
			Path filePath = Paths.get(bookEntity.getPath() + "/" + bookEntity.getFileName());

			PDDocument document = Loader.loadPDF(filePath.toFile());
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);
			stripper.setStartPage(startPage);
			stripper.setEndPage(endPage);

			content = stripper.getText(document);
			System.out.println(content);
			document.close();
		} catch (Exception e) {
			System.out.println(e);
			throw new GlobalException();
		}
		return content;
	}

}
