package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.Emprestimo;
import org.springframework.stereotype.Service;

@Service
public interface EmprestimoService {
    Iterable<Emprestimo> buscarTodos();

    Emprestimo buscarPorCliente(Cliente Cliente);

    Emprestimo buscarPorId(Long id);
    void inserir(Emprestimo emprestimo);
    void atualizar(Long id, Emprestimo emprestimo);
    void deletar(Long id);
}
