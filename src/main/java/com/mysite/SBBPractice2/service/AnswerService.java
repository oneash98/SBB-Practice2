package com.mysite.SBBPractice2.service;

import com.mysite.SBBPractice2.DataNotFoundException;
import com.mysite.SBBPractice2.domain.Answer;
import com.mysite.SBBPractice2.domain.Question;
import com.mysite.SBBPractice2.domain.SiteUser;
import com.mysite.SBBPractice2.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) throws IOException {
        Answer answer = new Answer();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/mysite/SBBPractice2/security/answerID.txt"))) {
            int id = Integer.parseInt(reader.readLine()) + 1;
            answer.setId(id);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mysite/SBBPractice2/security/answerID.txt", false))) {
                writer.write(Integer.toString(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
