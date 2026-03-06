package com.duck.devhub.controller;

import com.duck.devhub.dto.AnswerCreateRequestDto;
import com.duck.devhub.dto.AnswerResponseDto;
import com.duck.devhub.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

		private final AnswerService answerService;

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public AnswerResponseDto createAnswer(@Valid @RequestBody AnswerCreateRequestDto requestDto) {
				return answerService.createAnswer(requestDto);
		}

		@GetMapping("/question/{questionId}")
		public List<AnswerResponseDto> getAnswers(@PathVariable("questionId") Long questionId) {
				return answerService.getAnswers(questionId);
		}

		@PatchMapping("/{answerId}/accept")
		public void acceptAnswer(@PathVariable("answerId") Long answerId) {
				answerService.acceptAnswer(answerId);
		}
		
		@DeleteMapping("/{answerId}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteAnswer(@PathVariable("answerId") Long answerId) {
				answerService.deleteAnswer(answerId);
		}
		
}