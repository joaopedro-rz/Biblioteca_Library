package com.controle.Biblioteca.repository;

import com.controle.Biblioteca.model.Livro;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);
}
