package com.br.edu.infnet.leonardoLimaApi.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FieldMessage {

    private String fieldName;
    private String message;
}
