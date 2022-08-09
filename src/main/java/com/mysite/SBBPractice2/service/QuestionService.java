package com.mysite.SBBPractice2.service;


import com.mysite.SBBPractice2.DataNotFoundException;
import com.mysite.SBBPractice2.domain.Question;
import com.mysite.SBBPractice2.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;


    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content) throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/mysite/SBBPractice2/questionID.txt"));
        int id = Integer.parseInt(reader.readLine());
        reader.close();
        File oldFile = new File("src/main/java/com/mysite/SBBPractice2/questionID.txt");
        oldFile.delete();
        File newFile = new File("src/main/java/com/mysite/SBBPractice2/questionID.txt");
        try {
            FileWriter fileWriter = new FileWriter(newFile, false);
            fileWriter.write(Integer.toString(id + 1));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Question question = new Question();
        question.setId(id);
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
}
