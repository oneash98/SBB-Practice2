package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.Answer;
import com.mysite.SBBPractice2.domain.Question;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @AfterEach
    void afterEach() {
        this.answerRepository.deleteAll();
        this.questionRepository.deleteAll();
    }

    @Test
    void save() {
        Question question2 = new Question();
        question2.setSubject("스프링부트 모델 질문입니다.");
        question2.setContent("id는 자동으로 생성되나요?");
        question2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(question2);

        Answer answer = new Answer();
        answer.setContent("네 자동으로 생성됩니다");
        answer.setQuestion(this.questionRepository.findById(1).get());
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);

        assertThat("네 자동으로 생성됩니다").isEqualTo(this.answerRepository.findById(1).get().getContent());
        assertThat("스프링부트 모델 질문입니다.").isEqualTo(this.answerRepository.findById(1).get().getQuestion().getSubject());


    }

    @Transactional
    @Test
    void getAnswerList() {

        Optional<Question> oq = this.questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertThat(1).isEqualTo(answerList.size());
        assertThat("네 자동으로 생성됩니다").isEqualTo(answerList.get(0).getContent());
    }
}
