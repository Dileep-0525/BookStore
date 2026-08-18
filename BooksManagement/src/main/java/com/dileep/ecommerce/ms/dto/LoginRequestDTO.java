package com.dileep.ecommerce.ms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

	@Email(message = "Email is Required")
	@NotBlank
	private String username;
	@NotBlank(message = "Password is required")
	private String password;
	
}
