package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Client;
import com.br.edu.infnet.leonardoLimaApi.mapper.AddressMapper;
import com.br.edu.infnet.leonardoLimaApi.mapper.ClientMapper;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClientService implements CrudService<ClientDTO, Long> {

    private Map<Long, Client> clients = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    private final BCryptPasswordEncoder encoder;
    private final ClientMapper clientMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    @Autowired
    public ClientService(BCryptPasswordEncoder encoder, ClientMapper clientMapper, AddressMapper addressMapper, AddressService addressService) {
        this.encoder = encoder;
        this.clientMapper = clientMapper;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    @Override
    public List<ClientDTO> findAll() {
        return clients.values().stream().map(clientMapper::entityToDto).toList();
    }

    @Override
    public ClientDTO findById(Long id) {
        Client client = clients.get(id);
        if (Objects.nonNull(client)) {
            return clientMapper.entityToDto(client);
        }
        throw new ResourceNotFoundException("Cliente com id= " + id + " não encontrado.");
    }

    @Override
    public ClientDTO insert(ClientDTO dto) {
        Client client = clientMapper.dtoToEntity(dto);
        client.setId(index.getAndIncrement());
        client.setPassword(encoder.encode(dto.getPassword()));

        AddressDTO addressDto = null;
        if (Objects.nonNull(dto.getAddress())) {
            addressDto = new AddressDTO();
            addressDto.setClientId(client.getId());
            addressDto.setCep(dto.getAddress().getCep());
            addressDto = addressService.insert(addressDto);
        }

        client.setAddress(addressMapper.dtoToEntity(addressDto));
        clients.put(client.getId(), client);
        return clientMapper.entityToDto(client);
    }

    @Override
    public ClientDTO update(Long id, ClientDTO dto) {
        ClientDTO clientDto = findById(id);

        Client client = null;
        if (Objects.nonNull(clientDto)) {
            clients.remove(id);
            client = clientMapper.dtoToEntity(dto);
            client.setId(id);
            client.setPassword(encoder.encode(dto.getPassword()));

            AddressDTO address = addressService.update(clientDto.getAddress().getId(), dto.getAddress());
            client.setAddress(addressMapper.dtoToEntity(address));
            clients.put(client.getId(), client);
        }
        return clientMapper.entityToDto(client);
    }

    @Override
    public void delete(Long id) {
        ClientDTO clientDto = findById(id);
        if (Objects.nonNull(clientDto)) {
            clients.remove(id);
        }
    }
}
