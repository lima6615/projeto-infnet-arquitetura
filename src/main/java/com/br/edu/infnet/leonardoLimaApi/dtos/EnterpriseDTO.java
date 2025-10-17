package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnterpriseDTO extends UserDTO {

    private String photo;
    private String fantasy;
    private String cnpj;
}
