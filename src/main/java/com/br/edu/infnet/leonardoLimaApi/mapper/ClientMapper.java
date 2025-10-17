package com.br.edu.infnet.leonardoLimaApi.mapper;

import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO entityToDto(Client entity);

    Client dtoToEntity(ClientDTO dto);
}
