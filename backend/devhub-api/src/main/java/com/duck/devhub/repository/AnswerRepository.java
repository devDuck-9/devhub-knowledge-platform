package com.duck.devhub.repository;

import com.duck.devhub.entity.Answer;
import com.duck.devhub.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

		List<Answer> findByQuestion(Question question);
		List<Answer> findByQuestionOrderByCreatedAtAsc(Question question); //오름차순
}