package com.br.edu.infnet.leonardoLimaApi.repositories;

import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findCategoriesByInActiveIsTrue();

    Optional<Category> findByName(String name);
}
