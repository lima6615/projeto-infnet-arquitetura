package com.br.edu.infnet.leonardoLimaApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Campo Nome é obrigatório!")
    private String name;

    @NotBlank(message = "Campo E-mail é obrigatório!")
    @Email(message = "Informe um e-mail válido!")
    private String email;

    @NotBlank(message = "Campo Senha é obrigatório!")
    private String password;
    private Boolean inAtivo;
    private String phone;
}
