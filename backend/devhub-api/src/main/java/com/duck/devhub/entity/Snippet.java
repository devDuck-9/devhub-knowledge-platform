package com.duck.devhub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "snippets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snippet {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "snippet_id")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		@Column(name = "title", nullable = false, length = 120)
		private String title;

		@Column(name = "description", length = 500)
		private String description;

		@Column(name = "language", nullable = false, length = 30)
		private String language;

		@Lob
		@Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
		private String content;

		@Enumerated(EnumType.STRING)
		@Column(name = "visibility", nullable = false, length = 20)
		private SnippetVisibility visibility;
		
		@OneToMany(mappedBy = "snippet", fetch = FetchType.LAZY)
		private List<SnippetTag> snippetTags = new ArrayList<>();

		@Column(name = "created_at", nullable = false, updatable = false)
		private LocalDateTime createdAt;

		@Column(name = "updated_at", nullable = false)
		private LocalDateTime updatedAt;

		@Builder
		public Snippet(User user, String title, String description, String language, String content, SnippetVisibility visibility) {
				this.user = user;
				this.title = title;
				this.description = description;
				this.language = language;
				this.content = content;
				this.visibility = visibility;
		}

		@PrePersist
		protected void onCreate() {
				LocalDateTime now = LocalDateTime.now();
				this.createdAt = now;
				this.updatedAt = now;
				if (this.visibility == null) {
						this.visibility = SnippetVisibility.PRIVATE;
				}
		}

		@PreUpdate
		protected void onUpdate() {
				this.updatedAt = LocalDateTime.now();
		}

		public void updateSnippet(String title, String description, String language, String content, SnippetVisibility visibility) {
				this.title = title;
				this.description = description;
				this.language = language;
				this.content = content;
				this.visibility = visibility;
		}
}