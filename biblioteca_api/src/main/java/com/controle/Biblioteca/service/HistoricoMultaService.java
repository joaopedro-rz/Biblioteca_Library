package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.model.HistoricoMulta;
import org.springframework.stereotype.Service;

@Service
public interface HistoricoMultaService {

    Iterable<HistoricoMulta> buscarTodos();
    HistoricoMulta buscarPorHistoricoEmprestimo(HistoricoEmprestimo historicoEmprestimo);
    HistoricoMulta buscarPorId(Long id);
    void inserir(HistoricoMulta historicoMulta);
    void atualizar(Long id, HistoricoMulta historicoMulta);
    void deletar(Long id);
}
