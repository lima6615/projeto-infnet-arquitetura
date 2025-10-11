package com.br.edu.infnet.leonardoLimaApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class StandardErroDTO {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
