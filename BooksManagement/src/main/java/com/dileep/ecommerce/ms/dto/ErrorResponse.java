package com.dileep.ecommerce.ms.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

	private Boolean success;
	private String message;
	private int statusCode;
	private LocalDateTime timestamp;
	private Map<String, String> errors;

}
