package com.br.edu.infnet.leonardoLimaApi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "text")
    private String description;
    private Double price;

    @Column(name = "status")
    private Boolean inActive;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
