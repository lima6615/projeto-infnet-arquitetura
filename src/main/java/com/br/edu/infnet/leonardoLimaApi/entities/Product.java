package com.br.edu.infnet.leonardoLimaApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean inActive;
    private Category category = new Category();
}
