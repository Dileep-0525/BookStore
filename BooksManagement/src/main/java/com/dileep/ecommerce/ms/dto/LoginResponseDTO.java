package com.dileep.ecommerce.ms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

	private Long id;
	
	private String email;
	
	private String token;
}
