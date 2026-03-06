package com.duck.devhub.dto;

import java.util.List;

import com.duck.devhub.entity.QuestionVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionCreateRequestDto {

		@NotNull(message = "작성자 ID는 필수입니다.")
		private Long userId;

		@NotBlank(message = "제목은 필수입니다.")
		@Size(max = 150, message = "제목은 150자 이하로 입력해주세요.")
		private String title;

		@NotBlank(message = "내용은 필수입니다.")
		private String content;

		@NotNull(message = "공개 범위는 필수입니다.")
		private QuestionVisibility visibility;
		
		private List<String> tags;
}