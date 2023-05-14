package com.example.quizservice.exception;

public class QuizWithIdNotFoundException extends RuntimeException {

    private String message;

    public QuizWithIdNotFoundException() {
    }

    public QuizWithIdNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
