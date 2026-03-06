package com.duck.devhub.repository;

import com.duck.devhub.entity.QuestionTag;
import com.duck.devhub.entity.QuestionTagId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, QuestionTagId> {

		//QuestionTag -> Tag -> name 경로검색
		List<QuestionTag> findByTag_Name(String tagName);
}