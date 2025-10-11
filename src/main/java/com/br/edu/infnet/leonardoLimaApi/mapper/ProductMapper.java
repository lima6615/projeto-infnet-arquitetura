package com.br.edu.infnet.leonardoLimaApi.mapper;

import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO entityToDto(Product entity);

    @Mapping(source = "categoryId", target = "category.id")
    Product dtoToEntity(ProductDTO dto);
}
