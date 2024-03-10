package com.controle.Biblioteca.repository;

import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.model.Multa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MultaRepository extends CrudRepository<Multa, Long> {
    Optional<Multa> findByEmprestimo(Emprestimo emprestimo);
}
