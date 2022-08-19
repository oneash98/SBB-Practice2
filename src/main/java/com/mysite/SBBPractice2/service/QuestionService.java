package com.mysite.SBBPractice2.service;


import com.mysite.SBBPractice2.DataNotFoundException;
import com.mysite.SBBPractice2.domain.Question;
import com.mysite.SBBPractice2.domain.SiteUser;
import com.mysite.SBBPractice2.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;


    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {
        Question question = new Question();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/mysite/SBBPractice2/security/questionID.txt"))) {
            int id = Integer.parseInt(reader.readLine()) + 1;
            question.setId(id);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mysite/SBBPractice2/security/questionID.txt"))) {
                writer.write(Integer.toString(id));
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(user);
        this.questionRepository.save(question);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
