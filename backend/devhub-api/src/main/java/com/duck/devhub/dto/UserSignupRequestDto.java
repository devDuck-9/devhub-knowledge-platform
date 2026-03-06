package com.duck.devhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

		@NotBlank(message = "아이디는 필수입니다.")
		@Size(max = 30, message = "아이디는 30자 이하로 입력해주세요.")
		private String username;

		@Email(message = "올바른 이메일 형식이 아닙니다.")
		@Size(max = 255, message = "이메일은 255자 이하로 입력해주세요.")
		private String email;

		@NotBlank(message = "비밀번호는 필수입니다.")
		@Size(min = 4, max = 100, message = "비밀번호는 4자 이상 100자 이하로 입력해주세요.")
		private String password;
}