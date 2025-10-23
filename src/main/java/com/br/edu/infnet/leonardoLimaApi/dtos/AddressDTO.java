package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.br.edu.infnet.leonardoLimaApi.entities.Address;
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
public class AddressDTO {

    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;
    private Long clientId;
}
