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
public class ClientDTO extends UserDTO {

    private String cpf;
    private AddressDTO address;
}
