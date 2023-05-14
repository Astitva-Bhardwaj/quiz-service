package com.example.quizservice.repository;

import com.example.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("SELECT q FROM Quiz q WHERE q.startTime <= :now AND q.endTime >= :now")
    Quiz findActiveQuiz(LocalDateTime now);

//    @Query("SELECT new Result(q.rightAnswer) FROM Quiz q WHERE q.id = :quizId AND q.endTime <= :endTime")
//    int findQuizResult(Long quizId, LocalDateTime endTime);

    Quiz findQuizById(int id);
}
