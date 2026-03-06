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
public class SnippetTagId implements Serializable {

		@Column(name = "snippet_id")
		private Long snippetId;

		@Column(name = "tag_id")
		private Long tagId;

		public SnippetTagId(Long snippetId, Long tagId) {
				this.snippetId = snippetId;
				this.tagId = tagId;
		}
}