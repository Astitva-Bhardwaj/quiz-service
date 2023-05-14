package com.example.quizservice.controller;

import com.example.quizservice.exception.NoActiveQuizExistsException;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.service.QuizService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
@Slf4j
public class QuizController {

    @Autowired
    QuizService quizService;

    private final Bucket bucket;


    public QuizController()
    {
        Bandwidth limit = Bandwidth.classic(2, Refill.greedy(2, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz){
        log.info("QuizController - createQuiz");
        quizService.createQuiz(quiz);
        if(bucket.tryConsume(1))
        {
            return ResponseEntity.ok("Quiz created successfully");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/active")
    public Quiz getActiveQuiz() throws Exception {
        Quiz quiz = quizService.getActiveQuiz();
        if(quiz == null)
        {
            throw new NoActiveQuizExistsException("Active Quiz Not found");
        }
        return quiz;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Quiz> getAllQuizzes()
    {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}/result")
    //@ResponseStatus(HttpStatus.OK)
    public int getResult(@PathVariable int id){
        return quizService.getResult(id);
    }


}
