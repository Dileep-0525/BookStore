package com.dileep.ecommerce.ms.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	@NotNull(message="Name is required")
	private String name;
	
	private String organizationName;
	
	private String description;
	@Email
	private String email;
//	@NotNull(message = "Active status is required")
	private String password;
	
	private String mobile;
	@NotNull(message = "Internal status is required")
	private Boolean internal;
	
	private Long roleId;
	
	private Long createdBy;
	
	private Long updatedBy;
	@NotNull(message = "Active status is required")
	private Boolean active;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
