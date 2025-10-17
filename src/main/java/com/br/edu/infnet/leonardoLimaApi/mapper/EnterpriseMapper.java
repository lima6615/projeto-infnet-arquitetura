package com.br.edu.infnet.leonardoLimaApi.mapper;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Enterprise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {

    EnterpriseDTO entityToDto(Enterprise entity);

    Enterprise dtoToEntity(EnterpriseDTO dto);
}
