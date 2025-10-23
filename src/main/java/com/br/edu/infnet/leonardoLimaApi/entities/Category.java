package com.br.edu.infnet.leonardoLimaApi.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "status")
    private Boolean inActive;

    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Product> products = new HashSet<>();
}
