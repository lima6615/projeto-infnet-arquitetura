package com.br.edu.infnet.leonardoLimaApi.services;

import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import com.br.edu.infnet.leonardoLimaApi.services.exceptions.ResourceNotFoundException;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService implements CrudService<ProductDTO, Long> {

    private Map<Long, Product> products = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    @Override
    public List<ProductDTO> findAll() {
        return products.values().stream().map(ProductDTO::new).toList();
    }

    @Override
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        dtoToEntity(product, dto);
        product.setId(index.getAndIncrement());
        products.put(product.getId(), product);
        return dto;
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = products.get(id);
        if (product != null) {
            products.remove(product.getId());
            dtoToEntity(product, dto);
            products.put(product.getId(), product);
            return dto;
        }

        throw new ResourceNotFoundException("Produto com id " + id + " não encontrado.");
    }

    @Override
    public void delete(Long id) {
        Product product = products.get(id);
        if (product != null) {
            products.remove(id);
        } else {
            throw new ResourceNotFoundException("Produto com id " + id + " não encontrado.");
        }
    }

    private void dtoToEntity(Product entity, ProductDTO dto) {
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setInActive(dto.getInActive());
    }
}
