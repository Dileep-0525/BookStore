package com.dileep.ecommerce.ms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dileep.ecommerce.ms.config.FileStorageProperties;
import com.dileep.ecommerce.ms.dto.BookDTO;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.service.IBookService;
import com.dileep.ecommerce.ms.service.ITextToSpeechService;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ITextToSpeechService textToSpeechService;

	@Autowired
	private FileStorageProperties storageProperties;

	@PostMapping("/save")
	public BookDTO save(@RequestPart("book") String book, @RequestPart(required = false) MultipartFile file) {
		try {
//			ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
			BookDTO bookDTO = objectMapper.readValue(book, BookDTO.class);
			if (file != null) {
				bookDTO.setFileName(file.getOriginalFilename());
				bookDTO.setFileType(file.getContentType());
				bookDTO.setFile(file.getBytes());
			}
			bookDTO = bookService.save(bookDTO);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@PostMapping("/update/{id}")
	public BookDTO update(@PathVariable Long id, @RequestPart(value = "book") String book,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			System.out.println("update");
			BookDTO bookDTO = objectMapper.readValue(book, BookDTO.class);

			if (file != null && !file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				Path uploadPath = Paths.get(storageProperties.getPath());
				Files.createDirectories(uploadPath);

				Path targetPath = uploadPath.resolve(fileName);

				Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

				bookDTO.setFileName(file.getOriginalFilename());
				bookDTO.setFileType(file.getContentType());
				bookDTO.setFile(file.getBytes());
				bookDTO.setPath(uploadPath.toString());

			}
			bookDTO = bookService.update(id, bookDTO);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@GetMapping("/one/{id}")
	public BookDTO getById(@PathVariable Long id) {
		try {
			BookDTO bookDTO = bookService.getById(id);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@GetMapping("/all")
	public List<BookDTO> getAll() {
		try {
			List<BookDTO> list = bookService.getAll();
			return list;
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

	@PostMapping("/deleteOne/{id}")
	public BookDTO delete(@PathVariable Long id) {
		try {
			BookDTO bookDTO = bookService.delete(id);
			return bookDTO;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@PostMapping("/download/{id}/{fileName}")
	public ResponseEntity<?> getPdfByFileName(@PathVariable Long id, @PathVariable String fileName) {
		try {
			ResponseEntity<?> response = bookService.getPdf(id, fileName);
			return response;
		} catch (Exception e) {
			e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@GetMapping(value = "/audio/{bookId}", produces = "audio/wav")
	public ResponseEntity<byte[]> getAudio(@PathVariable Long bookId, @RequestParam Integer startPage,
			@RequestParam Integer endPage) throws IOException {

		String extractedText = bookService.getText(bookId, startPage, endPage);

		byte[] audio = textToSpeechService.convertTextToAudio(extractedText);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=book-audio.wav")
				.contentType(MediaType.parseMediaType("audio/wav")).body(audio);
	}

}
