package com.duck.devhub.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duck.devhub.dto.UserResponseDto;
import com.duck.devhub.dto.UserSignupRequestDto;
import com.duck.devhub.entity.User;
import com.duck.devhub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

		private final UserRepository userRepository;
		private final PasswordEncoder passwordEncoder;

		@Transactional
		public UserResponseDto signup(UserSignupRequestDto requestDto) {
				validateDuplicateUsername(requestDto.getUsername());
				validateDuplicateEmail(requestDto.getEmail());

				User user = User.builder()
								.username(requestDto.getUsername())
								.email(requestDto.getEmail())
								.passwordHash(passwordEncoder.encode(requestDto.getPassword())) //암호화 저장
								.build();

				User savedUser = userRepository.save(user);
				return new UserResponseDto(savedUser);
		}

		private void validateDuplicateUsername(String username) {
				if (userRepository.existsByUsername(username)) {
						throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
				}
		}

		private void validateDuplicateEmail(String email) {
				if (email != null && !email.isBlank() && userRepository.existsByEmail(email)) {
						throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
				}
		}
}