package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;

import java.util.List;

public interface ClientService {

    List<ClientDTO> findAll();

    ClientDTO findById(Long id);

    void delete(Long id);

    ClientDTO enableAndDisable(Long id, boolean status);

    ClientDTO updateClientAndAddress(Long id, ClientDTO dto, boolean update);

    ClientDTO createClientAndAddress(ClientDTO dto, boolean update);
}
