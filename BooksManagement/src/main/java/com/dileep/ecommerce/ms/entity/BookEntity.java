package com.dileep.ecommerce.ms.entity;

import java.time.LocalDate;

import org.springframework.data.convert.ValueConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Converts;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate publishedOn;
	
	@OneToOne
	@JoinColumn(name = "authorId")
	private AuthorEntity author;
	
	@OneToOne
	@JoinColumn(name = "categoryId")
	private BookCategoryEntity category;
	
	private String description;
	private Long numberOfDownloads;
	@Lob
	private byte[] file;
	private String fileName;
	private String fileType;

	
}
