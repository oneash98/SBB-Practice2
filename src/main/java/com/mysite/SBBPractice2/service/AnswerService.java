package com.mysite.SBBPractice2.service;

import com.mysite.SBBPractice2.domain.Answer;
import com.mysite.SBBPractice2.domain.Question;
import com.mysite.SBBPractice2.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/mysite/SBBPractice2/answerID.txt"));
        int id = Integer.parseInt(reader.readLine());
        reader.close();
        File oldFile = new File("src/main/java/com/mysite/SBBPractice2/answerID.txt");
        oldFile.delete();
        File newFile = new File("src/main/java/com/mysite/SBBPractice2/answerID.txt");
        try {
            FileWriter fileWriter = new FileWriter(newFile, false);
            fileWriter.write(Integer.toString(id + 1));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Answer answer = new Answer();
        answer.setId(id);
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}
