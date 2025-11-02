package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Address;
import com.br.edu.infnet.leonardoLimaApi.mapper.AddressMapper;
import com.br.edu.infnet.leonardoLimaApi.repositories.AddressRepository;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ApiViaCepException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class AddressService implements CrudService<AddressDTO, Long> {

    @Value("${spring.backend.host}")
    private String apiCep;

    private final WebClient webClient;
    private final AddressMapper addressMapper;
    private final AddressRepository repository;

    @Autowired
    public AddressService(WebClient webClient, AddressMapper addressMapper, AddressRepository repository) {
        this.webClient = webClient;
        this.addressMapper = addressMapper;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AddressDTO> findAll() {
        return repository.findAll().stream().map(addressMapper::entityToDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public AddressDTO findById(Long id) {
        return addressMapper.entityToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Endereço com o id = " + id + " não encontrada!")));
    }


    @Override
    public AddressDTO insert(AddressDTO dto) {
        Address address = findAddressByCep(dto.getCep());
        return addressMapper.entityToDto(address);
    }

    @Transactional
    @Override
    public AddressDTO update(Long id, AddressDTO dto) {
        this.findById(id);
        Address address = findAddressByCep(dto.getCep());
        address.setId(id);
        return addressMapper.entityToDto(address);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }

    public Address findAddressByCep(String cep) {
        return webClient.get()
                .uri(apiCep + "/{cep}/json", cep)
                .retrieve()
                .bodyToMono(Address.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(ex -> Mono.error(new ApiViaCepException("Erro ao buscar o CEP " + cep + " no site ViaCep!")))
                .block();
    }
}
