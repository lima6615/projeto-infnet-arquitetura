package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.mapper.CategotyMapper;
import com.br.edu.infnet.leonardoLimaApi.repositories.CategoryRepository;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.DatabaseException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceAlreadyExistsException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService implements CrudService<CategoryDTO, Long> {

    private final CategotyMapper categotyMapper;
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategotyMapper categotyMapper, CategoryRepository repository) {
        this.categotyMapper = categotyMapper;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> findAll() {
        return repository.findAll().stream().map(categotyMapper::entityToDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO findById(Long id) {
        return categotyMapper.entityToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Categoria com id " + id + " não encontrado.")));
    }

    @Transactional
    @Override
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = categotyMapper.dtoToEntity(dto);
        if (repository.findByName(dto.getName()).isEmpty()) {
            repository.save(category);
        } else {
            throw new ResourceAlreadyExistsException("Categoria com o nome = " + dto.getName() + " já cadastrada.");
        }
        return categotyMapper.entityToDto(category);
    }

    @Transactional
    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        this.findById(id);
        Category category = categotyMapper.dtoToEntity(dto);
        category.setId(id);
        return categotyMapper.entityToDto(repository.save(category));
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
