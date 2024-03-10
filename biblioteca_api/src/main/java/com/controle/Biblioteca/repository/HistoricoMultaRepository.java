package com.controle.Biblioteca.repository;

import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.model.HistoricoMulta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoricoMultaRepository extends CrudRepository<HistoricoMulta, Long> {
    Optional<HistoricoMulta> findByHistoricoEmprestimo(HistoricoEmprestimo historicoEmprestimo);
}
