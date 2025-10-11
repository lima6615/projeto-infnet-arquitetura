package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryDTO {

    private Long id;
    private String name;
    private Boolean inActive;
    private List<ProductDTO> products = new ArrayList<>();
}
