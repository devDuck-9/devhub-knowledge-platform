package com.duck.devhub.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(IllegalArgumentException.class)
		public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
				Map<String, String> response = new HashMap<>();
				response.put("message", e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
				Map<String, String> response = new HashMap<>();
				response.put("message", e.getBindingResult().getFieldError().getDefaultMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
}