package com.duck.devhub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.duck.devhub.entity.Question;
import com.duck.devhub.entity.QuestionVisibility;

public interface QuestionRepository extends JpaRepository<Question, Long> {

		Page<Question> findByVisibility(QuestionVisibility visibility, Pageable pageable);
		
		List<Question> findByTitleContainingIgnoreCaseOrContentContaining(String titleKeyword, String contentKeyword);
}