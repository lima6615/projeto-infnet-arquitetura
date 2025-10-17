package com.br.edu.infnet.leonardoLimaApi.controllers;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;
import com.br.edu.infnet.leonardoLimaApi.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/enterprises")
public class EnterpriseController {

    private final EnterpriseService service;

    @Autowired
    public EnterpriseController(EnterpriseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EnterpriseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnterpriseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EnterpriseDTO> insert(@RequestBody EnterpriseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EnterpriseDTO> update(@PathVariable Long id, @RequestBody EnterpriseDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
