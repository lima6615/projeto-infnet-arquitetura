package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import com.br.edu.infnet.leonardoLimaApi.mapper.CategotyMapper;
import com.br.edu.infnet.leonardoLimaApi.mapper.ProductMapper;
import com.br.edu.infnet.leonardoLimaApi.repositories.CategoryRepository;
import com.br.edu.infnet.leonardoLimaApi.repositories.ProductRepository;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceAlreadyExistsException;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryServiceImpl categoryService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CategotyMapper categotyMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, CategoryServiceImpl categoryService, CategoryRepository categoryRepository, ProductMapper productMapper, CategotyMapper categotyMapper) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.categotyMapper = categotyMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(productMapper::entityToDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO findById(Long id) {
        return productMapper.entityToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Produto com id " + id + " não encontrado.")));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findByCategory(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            return repository.findAllByCategory(categoryName).stream().map(productMapper::entityToDto).toList();
        }
        throw new ResourceNotFoundException("Produto não encontrado para a categoria=" + categoryName + " informada!");
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> filterProductByPrice(double priceStart, double priceEnd) {
        return repository.findByPriceBetween(priceStart, priceEnd).stream().map(productMapper::entityToDto).toList();
    }

    @Transactional
    @Override
    public ProductDTO insert(ProductDTO dto) {
        Product product = productMapper.dtoToEntity(dto);
        if (repository.findByName(product.getName()).isEmpty()) {
            product.setCategory(categotyMapper.dtoToEntity(categoryService.findById(dto.getCategoryId())));
            repository.save(product);
        } else {
            throw new ResourceAlreadyExistsException("Produto com o nome = " + dto.getName() + " já cadastrada.");
        }
        return productMapper.entityToDto(product);
    }

    @Transactional
    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        findById(id);
        Product product = productMapper.dtoToEntity(dto);
        product.setId(id);
        repository.save(product);
        return productMapper.entityToDto(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}
