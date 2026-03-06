package com.duck.devhub.repository;

import com.duck.devhub.entity.Question;
import com.duck.devhub.entity.Snippet;
import com.duck.devhub.entity.SnippetVisibility;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

		Page<Snippet> findByVisibility(SnippetVisibility visibility, Pageable pageable);
		
		List<Snippet> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String descriptionKeyword);
}