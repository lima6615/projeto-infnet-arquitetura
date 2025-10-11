package com.br.edu.infnet.leonardoLimaApi.controllers.exceptions;

import com.br.edu.infnet.leonardoLimaApi.dtos.StandardErroDTO;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceAdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErroDTO> notFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = request.getRequestURI();
        StandardErroDTO standard = new StandardErroDTO(Instant.now(), status.value(), e.getMessage(), path);
        return ResponseEntity.status(status).body(standard);
    }
}
