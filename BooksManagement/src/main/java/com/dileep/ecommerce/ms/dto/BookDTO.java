package com.dileep.ecommerce.ms.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
@Data
public class BookDTO implements Serializable{
	
	private Long id;
	private String name;
	private LocalDate publishedOn;
	private boolean newauthor;
	private String description;
	private Long numberOfDownloads;
	private byte[] file;
	private String fileName;
	private String fileType;
	private String authorName;
	private Long authorId;
	private boolean newCategory;
	private String categoryName;
	private Long categoryId;
}
