package com.br.edu.infnet.leonardoLimaApi.repositories;

import com.br.edu.infnet.leonardoLimaApi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
