package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Address;
import com.br.edu.infnet.leonardoLimaApi.mapper.AddressMapper;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ApiViaCepException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AddressService implements CrudService<AddressDTO, Long> {

    private Map<Long, Address> address = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    @Value("${spring.backend.host}")
    private String apiCep;

    private final WebClient webClient;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressService(WebClient webClient, AddressMapper addressMapper) {
        this.webClient = webClient;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<AddressDTO> findAll() {
        return address.values().stream().map(addressMapper::entityToDto).toList();
    }

    @Override
    public AddressDTO findById(Long id) {
        Address entity = address.get(id);
        if (Objects.nonNull(entity)) {
            return addressMapper.entityToDto(entity);
        }
        throw new ResourceNotFoundException("Endereço com o id = " + id + " não encontrada!");
    }

    @Override
    public AddressDTO insert(AddressDTO dto) {
        AddressDTO addDto = null;
        if (Objects.nonNull(dto.getCep())) {
            addDto = findAddressByCep(dto.getCep());
            addDto.setId(index.getAndIncrement());
            addDto.setClientId(dto.getClientId());
        }

        Address entity = addressMapper.dtoToEntity(addDto);
        address.put(entity.getId(), entity);
        return addressMapper.entityToDto(entity);
    }

    @Override
    public AddressDTO update(Long id, AddressDTO dto) {
        AddressDTO addDto = findById(id);
        address.remove(id);
        if (Objects.nonNull(dto.getCep())) {
            addDto = findAddressByCep(dto.getCep());
            addDto.setId(id);
            addDto.setClientId(addDto.getClientId());
        }

        Address entity = addressMapper.dtoToEntity(addDto);
        address.put(id, entity);
        return addressMapper.entityToDto(addressMapper.dtoToEntity(addDto));
    }

    @Override
    public void delete(Long id) {
        address.remove(id);
    }

    public AddressDTO findAddressByCep(String cep) {
        return webClient.get()
                .uri(apiCep + "/{cep}/json", cep)
                .retrieve()
                .bodyToMono(AddressDTO.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(ex -> {
                    Mono.error(new ApiViaCepException("Erro ao buscar o cep " + cep + " no site ViaCep!"));
                    return Mono.just(new AddressDTO());
                }).block();
    }
}
