package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    List<CategoryDTO> findActiveCategories();

    CategoryDTO insert(CategoryDTO dto);

    CategoryDTO update(Long id, CategoryDTO dto);

    void delete(Long id);
}
