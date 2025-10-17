package com.br.edu.infnet.leonardoLimaApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Enterprise extends User {

    private String photo;
    private String fantasy;
    private String cnpj;
}
