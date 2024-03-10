package com.controle.Biblioteca.service;

import com.controle.Biblioteca.model.Livro;
import org.springframework.stereotype.Service;

@Service
public interface LivroService {
    Iterable<Livro> buscarTodos();

    Livro buscarPorIsbn(String isbn);
    Livro buscarPorId(Long id);
    void inserir(Livro livro);
    void atualizar(Long id, Livro livro);
    void deletar(Long id);
}
