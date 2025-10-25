package com.br.edu.infnet.leonardoLimaApi.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class StandardErro {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
