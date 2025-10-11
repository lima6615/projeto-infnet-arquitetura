package com.br.edu.infnet.leonardoLimaApi.dtos;

import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryDTO {

    private Long id;
    private String name;
    private Boolean inActive;
    private Set<Product> products = new HashSet<>();

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.inActive = category.getInActive();
    }
}
