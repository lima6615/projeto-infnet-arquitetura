package com.br.edu.infnet.leonardoLimaApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_enterprise")
public class Enterprise extends User {

    private String photo;
    private String fantasy;
    private String cnpj;
}
