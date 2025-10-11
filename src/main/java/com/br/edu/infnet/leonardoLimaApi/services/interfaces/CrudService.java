package com.br.edu.infnet.leonardoLimaApi.services.interfaces;

import java.util.List;

/*
*   D -> DTO
*   ID -> Long
*/
public interface CrudService<D, ID> {
    List<D> findAll();

    D insert(D entity);

    D update(ID id, D entity);

    void delete(ID id);
}
