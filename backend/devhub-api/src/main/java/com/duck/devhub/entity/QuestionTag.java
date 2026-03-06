package com.duck.devhub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionTag {

		@EmbeddedId
		private QuestionTagId id;

		@ManyToOne(fetch = FetchType.LAZY)
		@MapsId("questionId")
		@JoinColumn(name = "question_id")
		private Question question;

		@ManyToOne(fetch = FetchType.LAZY)
		@MapsId("tagId")
		@JoinColumn(name = "tag_id")
		private Tag tag;

		public QuestionTag(Question question, Tag tag) {
				this.question = question;
				this.tag = tag;
				this.id = new QuestionTagId(question.getId(), tag.getId());
		}
}