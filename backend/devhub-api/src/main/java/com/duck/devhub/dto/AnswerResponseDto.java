package com.duck.devhub.dto;

import com.duck.devhub.entity.Answer;
import lombok.Getter;

@Getter
public class AnswerResponseDto {

		private final Long id;
		private final Long questionId;
		private final Long userId;
		private final String username;
		private final String content;
		private final boolean accepted;

		public AnswerResponseDto(Answer answer) {
				this.id = answer.getId();
				this.questionId = answer.getQuestion().getId();
				this.userId = answer.getUser().getId();
				this.username = answer.getUser().getUsername();
				this.content = answer.getContent();
				this.accepted = answer.isAccepted();
		}
}