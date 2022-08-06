package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
