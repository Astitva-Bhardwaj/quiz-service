package com.example.quizservice.service;

import com.example.quizservice.exception.QuizWithIdNotFoundException;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public void createQuiz(Quiz quiz) {
        log.info("QuizService -- createQuiz");
        quizRepository.save(quiz);
    }

    public Quiz getActiveQuiz() {
        LocalDateTime now = LocalDateTime.now();
        return quizRepository.findActiveQuiz(now);
    }


    public List<Quiz> getAllQuizzes() {
       return quizRepository.findAll();
    }

    public int getResult(int id) {
        Quiz quiz = quizRepository.findQuizById(id);
        if(quiz == null)
        {
            throw new QuizWithIdNotFoundException("Quiz with id " + id + " not found");
        }
        int rightAnswer = quiz.getRightAnswer();
        return rightAnswer;
    }
}
