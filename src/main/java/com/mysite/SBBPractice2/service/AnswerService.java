package com.mysite.SBBPractice2.service;

import com.mysite.SBBPractice2.domain.Answer;
import com.mysite.SBBPractice2.domain.Question;
import com.mysite.SBBPractice2.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) throws IOException {
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
        this.answerRepository.save(answer);
    }
}
