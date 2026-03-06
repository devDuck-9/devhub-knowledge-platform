package com.duck.devhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionTagId implements Serializable {

		@Column(name = "question_id")
		private Long questionId;

		@Column(name = "tag_id")
		private Long tagId;

		public QuestionTagId(Long questionId, Long tagId) {
				this.questionId = questionId;
				this.tagId = tagId;
		}
}