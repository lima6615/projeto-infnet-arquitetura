package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService implements CrudService<CategoryDTO, Long> {

    private Map<Long, Category> categories = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    @Override
    public List<CategoryDTO> findAll() {
        return categories.values().stream().map(CategoryDTO::new).toList();
    }

    @Override
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = new Category();
        dtoToEntity(category, dto);
        category.setId(index.getAndIncrement());
        categories.put(category.getId(), category);
        return dto;
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categories.get(id);
        if (category != null) {
            categories.remove(category.getId());
            dtoToEntity(category, dto);
            categories.put(category.getId(), category);
            return dto;
        }

        throw new ResourceNotFoundException("Categoria com id " + id + " não encontrado.");
    }

    @Override
    public void delete(Long id) {
        Category category = categories.get(id);
        if (category != null) {
            categories.remove(id);
        } else {
            throw new ResourceNotFoundException("Categoria com id " + id + " não encontrado.");
        }
    }

    private void dtoToEntity(Category entity, CategoryDTO dto) {
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setInActive(dto.getInActive());
    }
}
