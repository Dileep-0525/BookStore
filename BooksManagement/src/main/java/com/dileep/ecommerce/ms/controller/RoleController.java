package com.dileep.ecommerce.ms.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dileep.ecommerce.ms.dto.ApiResponse;
import com.dileep.ecommerce.ms.dto.ErrorResponse;
import com.dileep.ecommerce.ms.dto.RoleDTO;
import com.dileep.ecommerce.ms.service.IRoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody RoleDTO dto) {
		try {
			dto = roleService.save(dto);
		} catch (Exception e) {
			 return ResponseEntity.internalServerError()
		                .body(
		                        ErrorResponse.builder()
		                                .message("Failed to save role")
		                                .statusCode(500)
		                                .success(false)
		                                .timestamp(LocalDateTime.now())
		                                .build()
		                );
		}
		return ResponseEntity.ok(ApiResponse.<RoleDTO>builder().message(null).status(201).data(dto).success(true)
				.timestamp(LocalDateTime.now()).build());
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		List<RoleDTO> roleDTOs = null;
		try {
			roleDTOs = roleService.getAll();
		} catch (Exception e) {
			 return ResponseEntity.internalServerError()
		                .body(
		                        ErrorResponse.builder()
		                                .message("Failed to fetch roles")
		                                .statusCode(500)
		                                .success(false)
		                                .timestamp(LocalDateTime.now())
		                                .build()
		                );
		}
		return ResponseEntity.ok(ApiResponse.<RoleDTO>builder().message(null).status(200).list(roleDTOs).success(true)
				.timestamp(LocalDateTime.now()).build());
	}
	
	@GetMapping("/one")
	public ResponseEntity<?> getById(@RequestParam @Min(value = 1, message = "Id must be greater than 0") Long id) {
		RoleDTO dto = null; 
		try {
			dto = roleService.getById(id);
		} catch (Exception e) {
			 return ResponseEntity.internalServerError()
		                .body(
		                        ErrorResponse.builder()
		                                .message("Failed to fetch role")
		                                .statusCode(500)
		                                .success(false)
		                                .timestamp(LocalDateTime.now())
		                                .build()
		                );
		}
		return ResponseEntity.ok(ApiResponse.<RoleDTO>builder().message(null).status(200).data(dto).success(true)
				.timestamp(LocalDateTime.now()).build());
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam @Min(value = 1, message = "Id must be greater than 0") Long id) {
		
		try {
			 roleService.delete(id);
		} catch (Exception e) {
			 return ResponseEntity.internalServerError()
		                .body(
		                        ErrorResponse.builder()
		                                .message("Failed to delete role")
		                                .statusCode(500)
		                                .success(false)
		                                .timestamp(LocalDateTime.now())
		                                .build()
		                );
		}
		return ResponseEntity.ok(ApiResponse.builder().message(null).status(204).data("deleted Successfully").success(true)
				.timestamp(LocalDateTime.now()).build());
	}
	
}
