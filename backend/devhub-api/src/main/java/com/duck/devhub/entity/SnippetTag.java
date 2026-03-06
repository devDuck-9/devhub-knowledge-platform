package com.duck.devhub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "snippet_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippetTag {

		@EmbeddedId
		private SnippetTagId id;

		@ManyToOne(fetch = FetchType.LAZY)
		@MapsId("snippetId")
		@JoinColumn(name = "snippet_id")
		private Snippet snippet;

		@ManyToOne(fetch = FetchType.LAZY)
		@MapsId("tagId")
		@JoinColumn(name = "tag_id")
		private Tag tag;

		public SnippetTag(Snippet snippet, Tag tag) {
				this.snippet = snippet;
				this.tag = tag;
				this.id = new SnippetTagId(snippet.getId(), tag.getId());
		}
}