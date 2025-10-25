package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnterpriseDTO extends UserDTO {

    private String photo;
    private String fantasy;

    @NotBlank(message = "Campo CNPJ é obrigatório!")
    @CNPJ(message = "CNPJ informado inválido.")
    private String cnpj;
}
