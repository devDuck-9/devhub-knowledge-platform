package com.duck.devhub.controller;

import com.duck.devhub.dto.QuestionCreateRequestDto;
import com.duck.devhub.dto.QuestionResponseDto;
import com.duck.devhub.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

		private final QuestionService questionService;

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public QuestionResponseDto createQuestion(@Valid @RequestBody QuestionCreateRequestDto requestDto) {
				return questionService.createQuestion(requestDto);
		}
		
		@GetMapping("/search")
		public List<QuestionResponseDto> searchQuestions(
						@RequestParam(value = "tag", required = false) String tag,
						@RequestParam(value = "keyword", required = false) String keyword
		) {
				if (tag != null && !tag.isBlank()) {
						return questionService.searchQuestionsByTag(tag);
				}

				if (keyword != null && !keyword.isBlank()) {
						return questionService.searchQuestionsByKeyword(keyword);
				}

				return List.of();
		}

		@GetMapping
		public Page<QuestionResponseDto> getQuestions(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
				return questionService.getQuestions(pageable);
		}

		@GetMapping("/public")
		public Page<QuestionResponseDto> getPublicQuestions(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
				return questionService.getPublicQuestions(pageable);
		}

		@GetMapping("/{questionId}")
		public QuestionResponseDto getQuestion(@PathVariable("questionId") Long questionId) {
				return questionService.getQuestion(questionId);
		}

		@DeleteMapping("/{questionId}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteQuestion(@PathVariable("questionId") Long questionId) {
				questionService.deleteQuestion(questionId);
		}
		
}