package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Address;
import com.br.edu.infnet.leonardoLimaApi.entities.Client;
import com.br.edu.infnet.leonardoLimaApi.mapper.AddressMapper;
import com.br.edu.infnet.leonardoLimaApi.mapper.ClientMapper;
import com.br.edu.infnet.leonardoLimaApi.repositories.ClientRepository;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClientServiceImpl implements ClientService {

    private final BCryptPasswordEncoder encoder;
    private final ClientMapper clientMapper;
    private final AddressMapper addressMapper;
    private final AddressServiceImpl addressService;
    private final ClientRepository repository;


    @Autowired
    public ClientServiceImpl(BCryptPasswordEncoder encoder, ClientMapper clientMapper, AddressServiceImpl addressService, ClientRepository repository, AddressMapper addressMapper) {
        this.encoder = encoder;
        this.clientMapper = clientMapper;
        this.addressService = addressService;
        this.repository = repository;
        this.addressMapper = addressMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDTO> findAll() {
        return repository.findAll().stream().map(clientMapper::entityToDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDTO findById(Long id) {
        return clientMapper.entityToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente com id= " + id + " não encontrado.")));
    }

    @Transactional
    public ClientDTO createClientAndAddress(ClientDTO dto, boolean update) {
        Client client = clientMapper.dtoToEntity(dto);
        client.setPassword(encoder.encode(dto.getPassword()));
        return clientMapper.entityToDto(repository.save(getAddressDTO(dto, client, update)));
    }

    @Transactional
    public ClientDTO updateClientAndAddress(Long id, ClientDTO dto, boolean update) {
        ClientDTO clientDTO = this.findById(id);
        Client client = clientMapper.dtoToEntity(dto);
        client.setPassword(encoder.encode(dto.getPassword()));
        client.setId(id);
        client.setAddress(addressMapper.dtoToEntity(clientDTO.getAddress()));
        return clientMapper.entityToDto(repository.save(getAddressDTO(dto, client, update)));
    }

    @Transactional
    public ClientDTO enableAndDisable(Long id, boolean status) {
        ClientDTO clientDTO = this.findById(id);
        clientDTO.setInAtivo(status);
        Client entity = repository.save(clientMapper.dtoToEntity(clientDTO));
        return clientMapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }

    private Client getAddressDTO(ClientDTO dto, Client client, boolean update) {
        if (Objects.nonNull(dto.getAddress().getCep())) {

            AddressDTO addressDto = null;
            if (update) {
                addressDto = addressService.update(client.getAddress().getId(), dto.getAddress());
            } else {
                addressDto = addressService.insert(dto.getAddress());
            }

            Address address = addressMapper.dtoToEntity(addressDto);
            client.setAddress(address);
            address.setClient(client);
        }
        return client;
    }
}
