package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    Iterable<Cliente>buscarTodos();

    Cliente buscarPorCpf(String cpf);
    Cliente buscarPorId(Long id);
    void inserir(Cliente cliente);
    void atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}
