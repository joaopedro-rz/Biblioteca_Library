package com.controle.Biblioteca.repository;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.Emprestimo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {
    Optional<Emprestimo> findByCliente(Cliente Cliente);
}
