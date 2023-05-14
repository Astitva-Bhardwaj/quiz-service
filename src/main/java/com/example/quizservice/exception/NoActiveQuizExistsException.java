package com.example.quizservice.exception;

public class NoActiveQuizExistsException extends RuntimeException {


    private String message;

    public NoActiveQuizExistsException() {
    }

    public NoActiveQuizExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
