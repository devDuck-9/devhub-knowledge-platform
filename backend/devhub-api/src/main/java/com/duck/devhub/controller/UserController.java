package com.duck.devhub.controller;

import com.duck.devhub.dto.UserResponseDto;
import com.duck.devhub.dto.UserSignupRequestDto;
import com.duck.devhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

		private final UserService userService;

		@PostMapping("/signup")
		@ResponseStatus(HttpStatus.CREATED)
		public UserResponseDto signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
				return userService.signup(requestDto);
		}
}