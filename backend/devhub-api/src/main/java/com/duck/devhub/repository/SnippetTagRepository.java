package com.duck.devhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duck.devhub.entity.SnippetTag;
import com.duck.devhub.entity.SnippetTagId;

public interface SnippetTagRepository extends JpaRepository<SnippetTag, SnippetTagId> {
	
		List<SnippetTag> findByTag_Name(String tagName);
}