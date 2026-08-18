package com.dileep.ecommerce.ms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class AuthorDTO {

	private Long id;

	private String name;

	private LocalDate dateOfBirth;

	private String about;

	private List<BookDTO> bookDTOs;

	private byte[] photo;

	private String fileName;

	private String fileType;
}
