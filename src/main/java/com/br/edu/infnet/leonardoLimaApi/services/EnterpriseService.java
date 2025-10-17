package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Enterprise;
import com.br.edu.infnet.leonardoLimaApi.mapper.EnterpriseMapper;
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
public class EnterpriseService implements CrudService<EnterpriseDTO, Long> {

    private Map<Long, Enterprise> enterprises = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    private final EnterpriseMapper enterpriseMapper;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public EnterpriseService(EnterpriseMapper enterpriseMapper, BCryptPasswordEncoder encoder) {
        this.enterpriseMapper = enterpriseMapper;
        this.encoder = encoder;
    }

    @Override
    public List<EnterpriseDTO> findAll() {
        return enterprises.values().stream().map(enterpriseMapper::entityToDto).toList();
    }

    @Override
    public EnterpriseDTO findById(Long id) {
        Enterprise enterprise = enterprises.get(id);
        if (Objects.nonNull(enterprise)) {
            return enterpriseMapper.entityToDto(enterprise);
        }
        throw new ResourceNotFoundException("Empresa com o id= " + id + " não encontrado!");
    }

    @Override
    public EnterpriseDTO insert(EnterpriseDTO dto) {
        Enterprise enterprise = enterpriseMapper.dtoToEntity(dto);
        enterprise.setId(index.getAndIncrement());
        enterprise.setPassword(encoder.encode(dto.getPassword()));
        enterprises.put(enterprise.getId(), enterprise);
        return enterpriseMapper.entityToDto(enterprise);
    }

    @Override
    public EnterpriseDTO update(Long id, EnterpriseDTO dto) {
        EnterpriseDTO enterDto = findById(id);
        Enterprise enterprise = null;
        if (Objects.nonNull(enterDto)) {
            enterprises.remove(id);
            enterprise = enterpriseMapper.dtoToEntity(dto);
            enterprise.setId(id);
            enterprise.setPassword(encoder.encode(dto.getPassword()));
            enterprises.put(enterprise.getId(), enterprise);
        }
        return enterpriseMapper.entityToDto(enterprise);
    }

    @Override
    public void delete(Long id) {
        EnterpriseDTO enterpriseDto = findById(id);
        if (Objects.nonNull(enterpriseDto)) {
            enterprises.remove(id);
        }
    }
}
