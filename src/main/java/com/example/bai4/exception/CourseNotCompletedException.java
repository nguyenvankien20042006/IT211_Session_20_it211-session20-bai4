package com.example.bai4.exception;

public class CourseNotCompletedException extends RuntimeException {
    public CourseNotCompletedException(String message) {
        super(message);
    }
}
