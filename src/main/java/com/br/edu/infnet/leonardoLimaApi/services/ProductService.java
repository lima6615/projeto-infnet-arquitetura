package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import com.br.edu.infnet.leonardoLimaApi.mapper.CategotyMapper;
import com.br.edu.infnet.leonardoLimaApi.mapper.ProductMapper;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService implements CrudService<ProductDTO, Long> {

    private Map<Long, Product> products = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategotyMapper categotyMapper;

    @Autowired
    public ProductService(CategoryService categoryService, ProductMapper productMapper, CategotyMapper categotyMapper) {
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categotyMapper = categotyMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        return products.values().stream().map(productMapper::entityToDto).toList();
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = products.get(id);
        if (product != null) {
            return productMapper.entityToDto(product);
        }
        throw new ResourceNotFoundException("Produto com id " + id + " não encontrado.");
    }

    @Override
    public ProductDTO insert(ProductDTO dto) {

        CategoryDTO categoryDto = categoryService.findById(dto.getCategoryId());
        Product product = null;
        if (categoryDto.getId() != null) {
            product = productMapper.dtoToEntity(dto);
            product.setId(index.getAndIncrement());
            products.put(product.getId(), product);
        }

        Category category = categotyMapper.dtoToEntity(categoryDto);
        category.getProducts().add(product);
        categoryService.insert(categotyMapper.entityToDto(category));

        return productMapper.entityToDto(product);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        ProductDTO productDto = this.findById(id);
        CategoryDTO categoryDto = categoryService.findById(productDto.getCategoryId());

        Product product = null;
        if (Objects.nonNull(categoryService.findById(dto.getCategoryId()))) {
            if (Objects.nonNull(categoryDto)) {
                products.remove(productDto.getId());
                product = productMapper.dtoToEntity(dto);
                product.setId(id);
                products.put(productDto.getId(), product);

                if (!categoryDto.getProducts().isEmpty()) {
                    removeProductMap(categoryDto, productDto);
                    categoryService.insert(categoryDto);

                    CategoryDTO catDto = categoryService.findById(dto.getCategoryId());
                    catDto.getProducts().add(productMapper.entityToDto(product));
                    categoryService.insert(catDto);
                }
            }
        }
        return productMapper.entityToDto(product);
    }

    @Override
    public void delete(Long id) {
        ProductDTO productDto = this.findById(id);
        CategoryDTO categoryDto = categoryService.findById(productDto.getCategoryId());
        products.remove(id);
        removeProductMap(categoryDto, productDto);
        categoryService.insert(categoryDto);
    }


    private void removeProductMap(CategoryDTO categoryDto, ProductDTO productDto) {
        List<ProductDTO> list = new ArrayList<>();
        categoryDto.getProducts().forEach(p -> {
            if (p.getId().equals(productDto.getId())) {
                list.add(p);
            }
        });
        categoryDto.getProducts().removeAll(list);
    }
}
