package com.duck.devhub.service;

import com.duck.devhub.dto.QuestionResponseDto;
import com.duck.devhub.dto.SnippetCreateRequestDto;
import com.duck.devhub.dto.SnippetResponseDto;
import com.duck.devhub.entity.Snippet;
import com.duck.devhub.entity.SnippetTag;
import com.duck.devhub.entity.SnippetVisibility;
import com.duck.devhub.entity.Tag;
import com.duck.devhub.entity.User;
import com.duck.devhub.repository.SnippetRepository;
import com.duck.devhub.repository.SnippetTagRepository;
import com.duck.devhub.repository.TagRepository;
import com.duck.devhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SnippetService {

		private final SnippetRepository snippetRepository;
		private final UserRepository userRepository;
		private final TagRepository tagRepository;
		private final SnippetTagRepository snippetTagRepository;

		@Transactional
		public SnippetResponseDto createSnippet(SnippetCreateRequestDto requestDto) {
				User user = userRepository.findById(requestDto.getUserId())
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

				Snippet snippet = Snippet.builder()
								.user(user)
								.title(requestDto.getTitle())
								.description(requestDto.getDescription())
								.language(requestDto.getLanguage())
								.content(requestDto.getContent())
								.visibility(requestDto.getVisibility())
								.build();

				Snippet savedSnippet = snippetRepository.save(snippet);
				
				if (requestDto.getTags() != null) {
						for (String tagName : requestDto.getTags()) {
								Tag tag = tagRepository.findByName(tagName)
												.orElseGet(() -> tagRepository.save(new Tag(tagName)));
	
								SnippetTag snippetTag = new SnippetTag(savedSnippet, tag);
								snippetTagRepository.save(snippetTag);
						}
				}
				return new SnippetResponseDto(savedSnippet);
		}
		
		@Transactional(readOnly = true)
		public List<SnippetResponseDto> searchSnippetsByTag(String tagName) {
				return snippetTagRepository.findByTag_Name(tagName).stream()
								.map(snippetTag -> new SnippetResponseDto(snippetTag.getSnippet()))
								.toList();
		}
		
		@Transactional(readOnly = true)
		public List<SnippetResponseDto> searchSnippetsByKeyword(String keyword) {
				return snippetRepository
								.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
								.stream()
								.map(SnippetResponseDto::new)
								.toList();
		}
		
		@Transactional(readOnly = true)
		public Page<SnippetResponseDto> getSnippets(Pageable pageable) {
				return snippetRepository.findAll(pageable).map(SnippetResponseDto::new);
		}
		
		@Transactional(readOnly = true)
		public Page<SnippetResponseDto> getPublicSnippets(Pageable pageable) {
				return snippetRepository.findByVisibility(SnippetVisibility.PUBLIC,pageable).map(SnippetResponseDto::new);
		}
		
		@Transactional(readOnly = true)
		public SnippetResponseDto getSnippet(Long snippetId) {
				Snippet snippet = snippetRepository.findById(snippetId)
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스니펫입니다."));
	
				return new SnippetResponseDto(snippet);
		}
		
		@Transactional
		public void deleteSnippet(Long snippetId) {
				Snippet snippet = snippetRepository.findById(snippetId)
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스니펫입니다."));
	
				snippetRepository.delete(snippet);
//				snippetRepository.flush(); //원인확인
		}
		
}