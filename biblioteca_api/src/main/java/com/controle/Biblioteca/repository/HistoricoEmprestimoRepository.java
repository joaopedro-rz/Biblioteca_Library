package com.controle.Biblioteca.repository;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.HistoricoEmprestimo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricoEmprestimoRepository extends CrudRepository<HistoricoEmprestimo, Long> {
    Optional<HistoricoEmprestimo> findByCliente(Cliente cliente);
}
