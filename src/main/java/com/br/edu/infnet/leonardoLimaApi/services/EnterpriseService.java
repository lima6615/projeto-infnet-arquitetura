package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Enterprise;
import com.br.edu.infnet.leonardoLimaApi.mapper.EnterpriseMapper;
import com.br.edu.infnet.leonardoLimaApi.repositories.EnterpriseRepository;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.DatabaseException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnterpriseService implements CrudService<EnterpriseDTO, Long> {

    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public EnterpriseService(EnterpriseMapper enterpriseMapper, EnterpriseRepository repository, BCryptPasswordEncoder encoder) {
        this.enterpriseMapper = enterpriseMapper;
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EnterpriseDTO> findAll() {
        return repository.findAll().stream().map(enterpriseMapper::entityToDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public EnterpriseDTO findById(Long id) {
        return enterpriseMapper.entityToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Empresa com o id= " + id + " não encontrado!")));
    }

    @Transactional
    @Override
    public EnterpriseDTO insert(EnterpriseDTO dto) {
        Enterprise enterprise = enterpriseMapper.dtoToEntity(dto);
        enterprise.setPassword(encoder.encode(dto.getPassword()));
        return enterpriseMapper.entityToDto(repository.save(enterprise));
    }

    @Transactional
    @Override
    public EnterpriseDTO update(Long id, EnterpriseDTO dto) {
        this.findById(id);
        Enterprise enterprise = enterpriseMapper.dtoToEntity(dto);
        enterprise.setId(id);
        enterprise.setPassword(encoder.encode(dto.getPassword()));
        return enterpriseMapper.entityToDto(repository.save(enterprise));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade no banco de dados");
        }
    }
}
