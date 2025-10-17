package com.br.edu.infnet.leonardoLimaApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Address {

    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;
    private Client client;
}
