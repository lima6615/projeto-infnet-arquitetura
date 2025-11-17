package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;

import java.util.List;

public interface EnterpriseService {

    List<EnterpriseDTO> findAll();
    EnterpriseDTO findById(Long id);
    EnterpriseDTO findEnterprisesByCnpj(String cnpj);
    EnterpriseDTO insert(EnterpriseDTO dto);
    EnterpriseDTO enableAndDisable(Long id, boolean status);
    EnterpriseDTO update(Long id, EnterpriseDTO dto);
    void delete(Long id);
}
