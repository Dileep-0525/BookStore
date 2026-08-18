package com.dileep.ecommerce.ms.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

	private boolean success;

	private int status;

	private String message;

	private LocalDateTime timestamp;

	private T data;

	private List<T> list;
}