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
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean inActive;
    private Long categoryId;
}
