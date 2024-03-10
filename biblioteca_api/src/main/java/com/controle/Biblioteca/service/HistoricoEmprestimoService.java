package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.HistoricoEmprestimo;
import org.springframework.stereotype.Service;

@Service
public interface HistoricoEmprestimoService {

    Iterable<HistoricoEmprestimo> buscarTodos();
    HistoricoEmprestimo buscarPorCliente(Cliente cliente);
    HistoricoEmprestimo buscarPorId(Long id);
    void inserir(HistoricoEmprestimo emprestimo);
    void atualizar(Long id, HistoricoEmprestimo emprestimo);
    void deletar(Long id);
}
