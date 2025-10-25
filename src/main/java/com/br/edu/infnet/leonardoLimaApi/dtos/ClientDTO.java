package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientDTO extends UserDTO {

    @NotBlank(message = "Campo CPF é obrigatório!")
    @CPF(message = "CPF informado inválido.")
    private String cpf;
    private AddressDTO address;
}
