package com.dileep.ecommerce.ms.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "authors")
@Data
public class AuthorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private LocalDate dateOfBirth;
	
	private String about;
	
	@OneToMany
	@JoinColumn(name = "authorId")
	private  List<BookEntity> books;
	
	@Lob
	private byte[] photo;
	
	private String fileName;
	
	private String fileType;
	
}
