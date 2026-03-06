package com.duck.devhub.dto;

import java.util.List;

import com.duck.devhub.entity.Question;

import lombok.Getter;

@Getter
public class QuestionResponseDto {

		private final Long id;
		private final Long userId;
		private final String username;
		private final String title;
		private final String content;
		private final String visibility;
		private final int answerCount;
		private List<String> tags;

		public QuestionResponseDto(Question question) {
				this.id = question.getId();
				this.userId = question.getUser().getId();
				this.username = question.getUser().getUsername();
				this.title = question.getTitle();
				this.content = question.getContent();
				this.visibility = question.getVisibility().name();
				this.answerCount = question.getAnswers().size();
				this.tags = question.getQuestionTags().stream()
						.map(questionTag -> questionTag.getTag().getName())
						.toList();
		}
}