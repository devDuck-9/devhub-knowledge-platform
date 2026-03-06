package com.duck.devhub.service;

import com.duck.devhub.dto.QuestionCreateRequestDto;
import com.duck.devhub.dto.QuestionResponseDto;
import com.duck.devhub.entity.Question;
import com.duck.devhub.entity.QuestionTag;
import com.duck.devhub.entity.QuestionVisibility;
import com.duck.devhub.entity.Tag;
import com.duck.devhub.entity.User;
import com.duck.devhub.repository.QuestionRepository;
import com.duck.devhub.repository.QuestionTagRepository;
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
public class QuestionService {

		private final QuestionRepository questionRepository;
		private final UserRepository userRepository;
		private final TagRepository tagRepository;
		private final QuestionTagRepository questionTagRepository;

		@Transactional
		public QuestionResponseDto createQuestion(QuestionCreateRequestDto requestDto) {
				User user = userRepository.findById(requestDto.getUserId())
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

				Question question = Question.builder()
								.user(user)
								.title(requestDto.getTitle())
								.content(requestDto.getContent())
								.visibility(requestDto.getVisibility())
								.build();

				Question savedQuestion = questionRepository.save(question);
				
				//tag
				for (String tagName : requestDto.getTags()) {
						Tag tag = tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
						QuestionTag questionTag = new QuestionTag(savedQuestion, tag);
						questionTagRepository.save(questionTag);
				}
				
				return new QuestionResponseDto(savedQuestion);
		}
		
		//tag 검색
		@Transactional(readOnly = true)
		public List<QuestionResponseDto> searchQuestionsByTag(String tagName) {
				return questionTagRepository.findByTag_Name(tagName).stream()
								.map(questionTag -> new QuestionResponseDto(questionTag.getQuestion()))
								.toList();
		}
		//keyword 검색
		@Transactional(readOnly = true)
		public List<QuestionResponseDto> searchQuestionsByKeyword(String keyword) {
				return questionRepository
								.findByTitleContainingIgnoreCaseOrContentContaining(keyword, keyword)
								.stream()
								.map(QuestionResponseDto::new)
								.toList();
		}

		@Transactional(readOnly = true)
		public Page<QuestionResponseDto> getQuestions(Pageable pageable) {
			return questionRepository.findAll(pageable).map(QuestionResponseDto::new);
		}

		@Transactional(readOnly = true)
		public Page<QuestionResponseDto> getPublicQuestions(Pageable pageable) {
				return questionRepository.findByVisibility(QuestionVisibility.PUBLIC,pageable).map(QuestionResponseDto::new);
		}

		@Transactional(readOnly = true)
		public QuestionResponseDto getQuestion(Long questionId) {
				Question question = questionRepository.findById(questionId)
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
				return new QuestionResponseDto(question);
		}

		@Transactional
		public void deleteQuestion(Long questionId) {
				if (!questionRepository.existsById(questionId)) {
						throw new IllegalArgumentException("존재하지 않는 질문입니다.");
				}

				questionRepository.deleteById(questionId);
		}
}