package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.mapper.CategotyMapper;
import com.br.edu.infnet.leonardoLimaApi.mapper.ProductMapper;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService implements CrudService<CategoryDTO, Long> {

    private Map<Long, Category> categories = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    private final CategotyMapper categotyMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CategoryService(CategotyMapper categotyMapper, ProductMapper productMapper) {
        this.categotyMapper = categotyMapper;
        this.productMapper = productMapper;
    }


    @Override
    public List<CategoryDTO> findAll() {
        return categories.values().stream().map(categotyMapper::entityToDto).toList();
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categories.get(id);
        if (category != null) {
            return categotyMapper.entityToDto(category);
        }
        throw new ResourceNotFoundException("Categoria com id " + id + " não encontrado.");
    }

    @Override
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = categotyMapper.dtoToEntity(dto);

        if (Objects.isNull(dto.getId())) {
            category.setId(index.getAndIncrement());
        } else {
            categories.remove(category.getId());
        }
        categories.put(category.getId(), category);
        return categotyMapper.entityToDto(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        CategoryDTO categoryDto = findById(id);
        Category category = null;
        if (categoryDto != null) {
            categories.remove(categoryDto.getId());
            category = categotyMapper.dtoToEntity(dto);
            category.setId(id);
            category.getProducts().addAll(categoryDto.getProducts().stream().map(productMapper::dtoToEntity).toList());
            categories.put(categoryDto.getId(), category);
        }
        return categotyMapper.entityToDto(category);
    }

    @Override
    public void delete(Long id) {
        CategoryDTO categoryDto = findById(id);
        if (categoryDto != null) {
            categories.remove(id);
        }
    }
}
