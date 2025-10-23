package com.br.edu.infnet.leonardoLimaApi.repositories;

import com.br.edu.infnet.leonardoLimaApi.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
