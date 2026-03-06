package com.duck.devhub.dto;

import java.util.List;

import com.duck.devhub.entity.Snippet;
import lombok.Getter;

@Getter
public class SnippetResponseDto {

		private final Long id;
		private final Long userId;
		private final String username;
		private final String title;
		private final String description;
		private final String language;
		private final String content;
		private final String visibility;
		private List<String> tags;

		public SnippetResponseDto(Snippet snippet) {
				this.id = snippet.getId();
				this.userId = snippet.getUser().getId();
				this.username = snippet.getUser().getUsername();
				this.title = snippet.getTitle();
				this.description = snippet.getDescription();
				this.language = snippet.getLanguage();
				this.content = snippet.getContent();
				this.visibility = snippet.getVisibility().name();
				this.tags = snippet.getSnippetTags().stream()
						.map(snippetTag -> snippetTag.getTag().getName())
						.toList();
		}
}