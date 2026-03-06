package com.duck.devhub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "answer_id")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "question_id", nullable = false)
		private Question question;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		@Lob
		@Column(name = "content", nullable = false)
		private String content;

		@Column(name = "is_accepted", nullable = false)
		private boolean accepted;

		@Column(name = "created_at", nullable = false, updatable = false)
		private LocalDateTime createdAt;

		@Column(name = "updated_at", nullable = false)
		private LocalDateTime updatedAt;

		@Builder
		public Answer(Question question, User user, String content, boolean accepted) {
				this.question = question;
				this.user = user;
				this.content = content;
				this.accepted = accepted;
		}

		@PrePersist
		protected void onCreate() {
				LocalDateTime now = LocalDateTime.now();
				this.createdAt = now;
				this.updatedAt = now;
		}

		@PreUpdate
		protected void onUpdate() {
				this.updatedAt = LocalDateTime.now();
		}

		public void updateContent(String content) {
				this.content = content;
		}

		public void accept() {
				this.accepted = true;
		}

		public void unaccept() {
				this.accepted = false;
		}
}