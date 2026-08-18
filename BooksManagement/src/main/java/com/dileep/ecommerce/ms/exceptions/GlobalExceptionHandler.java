package com.dileep.ecommerce.ms.exceptions;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.dileep.ecommerce.ms.dto.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//
//		String message = "Invalid request payload";
//
//		Throwable rootCause = ex.getMostSpecificCause();
//		if (rootCause instanceof InvalidFormatException invalidFormatException) {
//			String fieldName = invalidFormatException.getPath().stream().findFirst()
//					.map(reference -> reference.getFieldName()).orElse("unknown");
//
//			String expectedType = invalidFormatException.getTargetType().getSimpleName();
//			message = String.format("Field '%s' must be of type %s", fieldName, expectedType);
//		}
//		ErrorResponse errorResponse = buildResponse(message, HttpStatus.BAD_REQUEST, null);
//
//		return ResponseEntity.badRequest().body(errorResponse);
//	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		Map<String, String> validationErrors = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String message = String.format("%s (rejected value: %s)", error.getDefaultMessage(),
					error.getRejectedValue());
			validationErrors.put(error.getField(), message);
		});
		ErrorResponse response = buildResponse("Validation failed", HttpStatus.BAD_REQUEST, validationErrors);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {

		String message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());

		ErrorResponse response = buildResponse(message, HttpStatus.BAD_REQUEST, null);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {

		Map<String, String> errors = new LinkedHashMap<>();

		ex.getConstraintViolations().forEach(v -> errors.put(v.getPropertyPath().toString(), v.getMessage()));

		ErrorResponse response = buildResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

		ErrorResponse response = buildResponse("Duplicate record or data integrity violation", HttpStatus.CONFLICT,
				null);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		ErrorResponse response = buildResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, null);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {

		ErrorResponse response = buildResponse("You do not have permission to perform this action",
				HttpStatus.FORBIDDEN, null);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

		ErrorResponse response = buildResponse("Uploaded file exceeds allowed size", HttpStatus.CONTENT_TOO_LARGE,
				null);

		return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body(response);
	}

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex) {

		return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE)
				.body(buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null));

	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {

		ErrorResponse response = buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, null);

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

//	@ExceptionHandler(ExpiredJwtException.class)
//	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
//
//		ErrorResponse response = buildResponse("Refresh token has expired", HttpStatus.UNAUTHORIZED, null);
//
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
//
//	@ExceptionHandler(MalformedJwtException.class)
//	public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
//
//		ErrorResponse response = buildResponse("Invalid token format", HttpStatus.UNAUTHORIZED, null);
//
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
//
//	@ExceptionHandler(SignatureException.class)
//	public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
//
//		ErrorResponse response = buildResponse("Invalid token signature", HttpStatus.UNAUTHORIZED, null);
//
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
//
//	@ExceptionHandler(JwtException.class)
//	public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
//
//		ErrorResponse response = buildResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
//
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
//	
//	@ExceptionHandler(InvalidTokenException.class)
//	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
//
//		ErrorResponse response = buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, null);
//
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
	
//	@ExceptionHandler({
//			        ExpiredJwtException.class,
//			        MalformedJwtException.class,
//			        SignatureException.class,
//			        JwtException.class,
//			        InvalidTokenException.class
//					})
//	public ResponseEntity<ErrorResponse> handleJwtExceptions(Exception ex) {
//	
//		String message = getJwtErrorMessage(ex);
//	
//		ErrorResponse response = buildResponse(message, HttpStatus.UNAUTHORIZED, null);
//	
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	}
//	
//	private String getJwtErrorMessage(Exception ex) {
//	
//		if (ex instanceof ExpiredJwtException) {
//			return "Refresh token has expired";
//		}
//	
//		if (ex instanceof MalformedJwtException) {
//			return "Invalid token format";
//		}
//	
//		if (ex instanceof SignatureException) {
//			return "Invalid token signature";
//		}
//	
//		if (ex instanceof InvalidTokenException) {
//			return ex.getMessage();
//		}
//	
//		return "Invalid token";
//	}
	
	private ErrorResponse buildResponse(String message, HttpStatus status, Map<String, String> errors) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage(message);
		response.setStatusCode(status.value());
		response.setTimestamp(LocalDateTime.now());
		response.setSuccess(false);
		response.setErrors(errors);
		return response;
	}

}
