package com.br.edu.infnet.leonardoLimaApi.controllers;

import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;
import com.br.edu.infnet.leonardoLimaApi.services.interfaces.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/clients")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestParam(name = "update", defaultValue = "false") boolean update,
                                            @Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createClientAndAddress(dto, update));
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<ClientDTO> enableAndDisable(@PathVariable Long id, @RequestParam(name = "status") boolean status) {
        return ResponseEntity.ok().body(service.enableAndDisable(id, status));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@RequestParam(name = "update", defaultValue = "false") boolean update,
                                            @PathVariable Long id,
                                            @RequestBody ClientDTO dto) {
        return ResponseEntity.ok().body(service.updateClientAndAddress(id, dto, update));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
