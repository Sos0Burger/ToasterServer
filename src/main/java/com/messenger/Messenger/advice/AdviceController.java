package com.messenger.Messenger.advice;

import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.exception.UploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundException(NotFoundException exception){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> uploadException(UploadException exception){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
