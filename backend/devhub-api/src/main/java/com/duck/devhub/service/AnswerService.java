package com.duck.devhub.service;

import com.duck.devhub.dto.AnswerCreateRequestDto;
import com.duck.devhub.dto.AnswerResponseDto;
import com.duck.devhub.entity.Answer;
import com.duck.devhub.entity.Question;
import com.duck.devhub.entity.User;
import com.duck.devhub.repository.AnswerRepository;
import com.duck.devhub.repository.QuestionRepository;
import com.duck.devhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

		private final AnswerRepository answerRepository;
		private final QuestionRepository questionRepository;
		private final UserRepository userRepository;

		@Transactional
		public AnswerResponseDto createAnswer(AnswerCreateRequestDto requestDto) {

				Question question = questionRepository.findById(requestDto.getQuestionId())
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

				User user = userRepository.findById(requestDto.getUserId())
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

				Answer answer = Answer.builder()
								.question(question)
								.user(user)
								.content(requestDto.getContent())
								.accepted(false)
								.build();

				Answer savedAnswer = answerRepository.save(answer);

				return new AnswerResponseDto(savedAnswer);
		}

		@Transactional(readOnly = true)
		public List<AnswerResponseDto> getAnswers(Long questionId) {

				Question question = questionRepository.findById(questionId)
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

				return answerRepository.findByQuestionOrderByCreatedAtAsc(question)
								.stream()
								.map(AnswerResponseDto::new)
								.toList();
		}

		@Transactional
		public void acceptAnswer(Long answerId) {

				Answer answer = answerRepository.findById(answerId)
								.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
				
				//답변채택은 1개만 가능
				Question question = answer.getQuestion();
				List<Answer> answers = answerRepository.findByQuestion(question);
				
				//기존 답변 전부 채택 해제
				for (Answer a : answers) {
					a.unaccept();
				}
				answer.accept();
		}
		
		@Transactional
		public void deleteAnswer(Long answerId) {

				if (!answerRepository.existsById(answerId)) {
						throw new IllegalArgumentException("존재하지 않는 답변입니다.");
				}

				answerRepository.deleteById(answerId);
		}
		
}