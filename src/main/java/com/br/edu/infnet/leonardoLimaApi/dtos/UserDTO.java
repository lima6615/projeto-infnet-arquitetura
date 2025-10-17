package com.br.edu.infnet.leonardoLimaApi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean inAtivo;
    private String phone;
}
