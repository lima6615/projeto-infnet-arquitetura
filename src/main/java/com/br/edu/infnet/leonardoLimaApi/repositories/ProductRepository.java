package com.br.edu.infnet.leonardoLimaApi.repositories;

import com.br.edu.infnet.leonardoLimaApi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT P FROM Product P JOIN Category C on C.id = P.category.id WHERE C.name= :category ")
    List<Product> findAllByCategory(String category);

    Optional<Product> findByName(String name);
    
    List<Product> findByPriceBetween(Double priceStart, Double priceEnd);
}
