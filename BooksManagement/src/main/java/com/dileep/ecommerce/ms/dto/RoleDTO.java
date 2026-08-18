package com.dileep.ecommerce.ms.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
   
	@Positive(message = "Id must be greater than 0")
	private Long id;
	@NotBlank(message = "Name is required")
	private String name;
}
