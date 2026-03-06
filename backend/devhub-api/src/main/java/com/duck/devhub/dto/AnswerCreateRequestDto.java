package com.duck.devhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerCreateRequestDto {

		@NotNull(message = "질문 ID는 필수입니다.")
		private Long questionId;

		@NotNull(message = "작성자 ID는 필수입니다.")
		private Long userId;

		@NotBlank(message = "답변 내용은 필수입니다.")
		private String content;
}