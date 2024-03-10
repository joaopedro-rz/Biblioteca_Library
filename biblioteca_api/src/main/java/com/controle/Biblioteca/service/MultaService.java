package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.model.Multa;
import org.springframework.stereotype.Service;

@Service
public interface MultaService {

    Iterable<Multa> buscarTodos();

    Multa buscarPorEmprestimo(Emprestimo emprestimo);
    Multa buscarPorId(Long id);
    void inserir(Multa multa);
    void atualizar(Long id, Multa multa);
    void deletar(Long id);
}
