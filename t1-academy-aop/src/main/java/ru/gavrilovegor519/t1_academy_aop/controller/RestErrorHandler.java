package ru.gavrilovegor519.t1_academy_aop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.gavrilovegor519.t1_academy_aop.exception.TaskNotFound;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFound ex) {
        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }
}