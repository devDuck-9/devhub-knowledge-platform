package com.duck.devhub.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "question_id")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		@Column(name = "title", nullable = false, length = 150)
		private String title;

		@Lob
		@Column(name = "content", nullable = false)
		private String content;

		@Enumerated(EnumType.STRING)
		@Column(name = "visibility", nullable = false, length = 20)
		private QuestionVisibility visibility;
		
		@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
		private List<Answer> answers = new ArrayList<>();
		
		@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
		private List<QuestionTag> questionTags = new ArrayList<>();

		@Column(name = "created_at", nullable = false, updatable = false)
		private LocalDateTime createdAt;

		@Column(name = "updated_at", nullable = false)
		private LocalDateTime updatedAt;

		@Builder
		public Question(User user, String title, String content, QuestionVisibility visibility) {
				this.user = user;
				this.title = title;
				this.content = content;
				this.visibility = visibility;
		}

		@PrePersist
		protected void onCreate() {
				LocalDateTime now = LocalDateTime.now();
				this.createdAt = now;
				this.updatedAt = now;
				if (this.visibility == null) {
						this.visibility = QuestionVisibility.PUBLIC;
				}
		}

		@PreUpdate
		protected void onUpdate() {
				this.updatedAt = LocalDateTime.now();
		}

		public void updateQuestion(String title, String content, QuestionVisibility visibility) {
				this.title = title;
				this.content = content;
				this.visibility = visibility;
		}
}