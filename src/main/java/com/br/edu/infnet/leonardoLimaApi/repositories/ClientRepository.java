package com.br.edu.infnet.leonardoLimaApi.repositories;

import com.br.edu.infnet.leonardoLimaApi.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
