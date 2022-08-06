package com.mysite.SBBPractice2.repository;

import com.mysite.SBBPractice2.domain.Question;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    Integer id = 0;

    @AfterEach
    public void afterEach() {
        this.questionRepository.deleteAll();
    }

    @Test
    public void save() {

        Question question1 = new Question();
        question1.setId(++id);
        question1.setSubject("sbb가 무엇인가요?");
        question1.setContent("sbb에 대해서 알고 싶습니다.");
        question1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(question1);

        Question question2 = new Question();
        question2.setId(++id);
        question2.setSubject("스프링부트 모델 질문입니다.");
        question2.setContent("id는 자동으로 생성되나요?");
        question2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(question2);

        assertThat("sbb가 무엇인가요?").isEqualTo(this.questionRepository.findById(1).get().getSubject());
        assertThat("스프링부트 모델 질문입니다.").isEqualTo(this.questionRepository.findById(2).get().getSubject());
        assertThat(2).isEqualTo(this.questionRepository.findAll().size());
    }

    @Test
    void findBySubject() {
        this.save();

        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(1).isEqualTo(q.getId());
    }

    @Test
    void updateSubject() {
        this.save();

        Optional<Question> oq = this.questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);

        assertThat("수정된 제목").isEqualTo(this.questionRepository.findById(1).get().getSubject());
    }

    @Test
    void delete() {
        this.save();
        Optional<Question> oq = this.questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertThat(1).isEqualTo(this.questionRepository.count());
    }
}
