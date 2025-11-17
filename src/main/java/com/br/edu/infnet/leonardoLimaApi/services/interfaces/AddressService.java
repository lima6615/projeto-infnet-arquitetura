package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> findAll();

    AddressDTO findById(Long id);

    AddressDTO insert(AddressDTO dto);

    AddressDTO update(Long id, AddressDTO dto);

    void delete(Long id);
}
