package com.br.edu.infnet.leonardoLimaApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean inAtivo;
    private String phone;
}
