package com.br.edu.infnet.leonardoLimaApi.mapper;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategotyMapper {

    CategoryDTO entityToDto(Category entity);

    Category dtoToEntity(CategoryDTO dto);
}
