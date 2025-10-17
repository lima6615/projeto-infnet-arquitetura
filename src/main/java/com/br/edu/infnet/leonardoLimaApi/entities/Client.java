package com.br.edu.infnet.leonardoLimaApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client extends User {

    private String cpf;
    private Address address;
}
