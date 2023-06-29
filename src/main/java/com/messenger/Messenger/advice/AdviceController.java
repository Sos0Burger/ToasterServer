package com.messenger.Messenger.advice;

import com.messenger.Messenger.exception.*;
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
    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> alreadyExistsException(AlreadyExistsException exception){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> authException(AuthException exception){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
