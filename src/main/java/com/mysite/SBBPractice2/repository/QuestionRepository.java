package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.Question;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);
}
