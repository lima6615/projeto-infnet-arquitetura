package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    List<ProductDTO> findByCategory(String categoryName);

    List<ProductDTO> filterProductByPrice(double priceStart, double priceEnd);

    ProductDTO insert(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
}
