package com.dileep.ecommerce.ms.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.dileep.ecommerce.ms.dto.ChangePasswordDTO;
import com.dileep.ecommerce.ms.dto.ErrorResponse;
import com.dileep.ecommerce.ms.dto.UserDTO;
import com.dileep.ecommerce.ms.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

		private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

		@Autowired
		private IUserService userService;

		@PostMapping("/save")
		public ResponseEntity<?> save(@Valid @RequestBody UserDTO dto) {

			try {
				dto = userService.save(dto);
			} catch (Exception e) {
				LOGGER.error("Exception occured while saving user", dto, e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to save user")
						.statusCode(500).success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.<UserDTO>builder().message(null).status(201).data(dto).success(true)
					.timestamp(LocalDateTime.now()).build());
		}
		
		@PostMapping("/createAdmin")
		public ResponseEntity<?> createAdmin() {
			UserDTO dto = new UserDTO();
			try {
				
//				dto.setId(7l);
				dto.setName("Admin");
				dto.setEmail("admin@gmail.com");
				dto.setPassword("Stay@focus");
				dto.setRoleId(1l);
				dto.setActive(true);
				dto = userService.save(dto);
			}catch (Exception e) {
				LOGGER.error("Exception occured while saving user", dto, e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to save user")
						.statusCode(500).success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.<UserDTO>builder().message(null).status(201).data(dto).success(true)
					.timestamp(LocalDateTime.now()).build());
		}
		
		@PostMapping("/defeaultUser/save")
		public ResponseEntity<?> defeaultUser(@Valid @RequestBody UserDTO dto) {

			try {
				dto = userService.save(dto);
			} catch (Exception e) {
				LOGGER.error("Exception occured while saving user", dto, e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to save user")
						.statusCode(500).success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.<UserDTO>builder().message(null).status(201).data(dto).success(true)
					.timestamp(LocalDateTime.now()).build());
		}

		@GetMapping("/all")
		public ResponseEntity<?> getAll() {
			List<UserDTO> UserDTOs = null;
			try {
				UserDTOs = userService.getAll();
			} catch (Exception e) {
				LOGGER.error("Exception occured while fetching role list ", e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to fetch user")
						.statusCode(500).success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.<UserDTO>builder().message(null).status(200).list(UserDTOs).success(true)
					.timestamp(LocalDateTime.now()).build());
		}
		
		@GetMapping("/one")
		public ResponseEntity<?> getById(@RequestParam Long id) {
			UserDTO dto = null;
			try {
				dto = userService.getById(id);
			} catch (Exception e) {
				LOGGER.error("Exception occured while fetching role with id: ", id, e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to fetch user")
						.statusCode(500).success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.<UserDTO>builder().message(null).status(200).data(dto).success(true)
					.timestamp(LocalDateTime.now()).build());
		}

		@DeleteMapping("/delete")
		public ResponseEntity<?> deleteyId(@RequestParam Long id) {

			try {
				userService.delete(id);
			} catch (Exception e) {
				LOGGER.error("Exception occured while deleting user with id: ", id, e);
				return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Failed to delete user")
						.statusCode(500)
						.success(false).timestamp(LocalDateTime.now()).build());
			}
			return ResponseEntity.ok(ApiResponse.builder().message(null).status(204).data("deleted Successfully")
					.success(true).timestamp(LocalDateTime.now()).build());
		}

		@PostMapping("/change-password")
		public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto){

			userService.changePassword(dto);

			return ResponseEntity.ok("Password Changed Successfully");
		}

	
}
