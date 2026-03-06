package com.duck.devhub.dto;

import java.util.List;

import com.duck.devhub.entity.SnippetVisibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SnippetCreateRequestDto {

		@NotNull(message = "작성자 ID는 필수입니다.")
		private Long userId;

		@NotBlank(message = "제목은 필수입니다.")
		@Size(max = 120, message = "제목은 120자 이하로 입력해주세요.")
		private String title;

		@Size(max = 500, message = "설명은 500자 이하로 입력해주세요.")
		private String description;

		@NotBlank(message = "언어는 필수입니다.")
		@Size(max = 30, message = "언어는 30자 이하로 입력해주세요.")
		private String language;

		@NotBlank(message = "코드 내용은 필수입니다.")
		private String content;

		@NotNull(message = "공개 범위는 필수입니다.")
		private SnippetVisibility visibility;

		private List<String> tags;
}