package com.finch.webfinch.service;

import com.finch.webfinch.model.Cliente;
import java.util.List;

/**
 * Interface do serviço do Cliente.
 *
 * @author guilherme.carvalho
 */
public interface ClienteService {
    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente create(Cliente cliente);
    
    Cliente update(Cliente cliente);

    void remove(Long id);
}

