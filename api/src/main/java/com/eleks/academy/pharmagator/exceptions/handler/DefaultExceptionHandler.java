package com.eleks.academy.pharmagator.exceptions.handler;

import com.eleks.academy.pharmagator.exceptions.FileUploadException;
import com.eleks.academy.pharmagator.exceptions.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(IdentifierNotFoundException.class)
    public ResponseEntity<Response> handleIdentifierNotFoundException(IdentifierNotFoundException ex) {
        log.info(ex.getMessage());

        Response response = Response.builder().message(ex.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<Response> handleFileUploadException(FileUploadException ex) {
        log.info(ex.getMessage());

        Response response = Response.builder().message(ex.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


}
