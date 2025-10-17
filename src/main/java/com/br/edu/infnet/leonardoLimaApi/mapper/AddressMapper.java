package com.br.edu.infnet.leonardoLimaApi.mapper;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "client.id", target = "clientId")
    AddressDTO entityToDto(Address entity);

    @Mapping(source = "clientId", target = "client.id")
    Address dtoToEntity(AddressDTO dto);
}
