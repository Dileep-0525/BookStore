package com.dileep.ecommerce.ms.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dileep.ecommerce.ms.dto.ApiResponse;
import com.dileep.ecommerce.ms.dto.LoginRequestDTO;
import com.dileep.ecommerce.ms.dto.LoginResponseDTO;
import com.dileep.ecommerce.ms.exceptions.AuthenticationException;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.service.ILoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class LoginController {

	@Autowired
	private ILoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> postMethodName(@Valid @RequestBody LoginRequestDTO requestDTO) {
		LoginResponseDTO responseDTO = null;
		try {
			responseDTO = loginService.login(requestDTO);
		} catch (AuthenticationException e) {
			throw new AuthenticationException(e.getMessage());
		} catch (Exception e) {
			throw new GlobalException();
		}
		return ResponseEntity.ok(ApiResponse.<LoginResponseDTO>builder().message("Success").status(200)
				.data(responseDTO).success(true).timestamp(LocalDateTime.now()).build());
	}

	
}
