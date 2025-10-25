package com.br.edu.infnet.leonardoLimaApi.controllers.exceptions;

import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceAlreadyExistsException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErro> notFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = request.getRequestURI();
        StandardErro standard = new StandardErro(Instant.now(), status.value(), e.getMessage(), path);
        return ResponseEntity.status(status).body(standard);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardErro> createError(ResourceAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        StandardErro standard = new StandardErro(Instant.now(), status.value(), e.getMessage(), path);
        return ResponseEntity.status(status).body(standard);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String path = request.getRequestURI();
        ValidationError error = new ValidationError(Instant.now(), status.value(), null, path);

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            FieldMessage field = new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage());
            error.getMessages().add(field);
        }

        return ResponseEntity.status(status).body(error);
    }
}
