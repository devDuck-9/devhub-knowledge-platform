package com.duck.devhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.duck.devhub.dto.SnippetCreateRequestDto;
import com.duck.devhub.dto.SnippetResponseDto;
import com.duck.devhub.service.SnippetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/snippets")
public class SnippetController {

		private final SnippetService snippetService;

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public SnippetResponseDto createSnippet(@Valid @RequestBody SnippetCreateRequestDto requestDto) {
				return snippetService.createSnippet(requestDto);
		}
		
		@GetMapping("/search")
		public List<SnippetResponseDto> searchSnippets(
						@RequestParam(value = "tag", required = false) String tag,
						@RequestParam(value = "keyword", required = false) String keyword
		) {
				if (tag != null && !tag.isBlank()) {
						return snippetService.searchSnippetsByTag(tag);
				}

				if (keyword != null && !keyword.isBlank()) {
						return snippetService.searchSnippetsByKeyword(keyword);
				}

				return List.of();
		}
		
		@GetMapping
		public Page<SnippetResponseDto> getSnippets(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
				return snippetService.getSnippets(pageable);
		}
		
		@GetMapping("/public")
		public Page<SnippetResponseDto> getPublicSnippets(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
				return snippetService.getPublicSnippets(pageable);
		}
		
		@GetMapping("/{snippetId}")
		public SnippetResponseDto getSnippet(@PathVariable("snippetId") Long snippetId) {
				return snippetService.getSnippet(snippetId);
		}
		
		@DeleteMapping("/{snippetId}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteSnippet(@PathVariable("snippetId") Long snippetId) {
				snippetService.deleteSnippet(snippetId);
		}
		
}