package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.model.Livro;
import com.controle.Biblioteca.repository.LivroRepository;
import com.controle.Biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Override
    public Iterable<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    @Override
    public Livro buscarPorIsbn(String isbn) {
        Optional<Livro> livroOptional = livroRepository.findByIsbn(isbn);
        return livroOptional.orElse(null);
    }

    @Override
    public Livro buscarPorId(Long id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        return livroOptional.orElse(null);
    }

    @Override
    public void inserir(Livro livro) {
        livro.setId(null);
        livroRepository.save(livro);
    }

    @Override
    public void atualizar(Long id, Livro livro) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        if(livroOptional.isPresent()) livroRepository.save(livro);
    }

    @Override
    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }
}
