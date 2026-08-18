package com.dileep.ecommerce.ms.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate publishedOn;
	
	@OneToOne
	@JoinColumn(name = "authorId")
	private AuthorEntity author;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private BookCategoryEntity category;
	
	private String description;
	private Long numberOfDownloads;
//	@Lob this may create save error
	@Lob
	private byte[] file;
	private String fileName;
	private String fileType;
	private String path;
//	@Lob
	private byte[] thumbnail;
	
	private Double price;
	
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 512)
	private float[] coverEmbedding;
	
}
