package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.repository.EmprestimoRepository;
import com.controle.Biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Override
    public Iterable<Emprestimo> buscarTodos() {
        return emprestimoRepository.findAll();
    }

    @Override
    public Emprestimo buscarPorCliente(Cliente Cliente) {
        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findByCliente(Cliente);
        return emprestimoOptional.orElse(null);
    }

    @Override
    public Emprestimo buscarPorId(Long id) {
        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(id);
        return emprestimoOptional.orElse(null);
    }

    @Override
    public void inserir(Emprestimo emprestimo) {
        emprestimo.setId(null);
        emprestimoRepository.save(emprestimo);
    }

    @Override
    public void atualizar(Long id, Emprestimo emprestimo) {
        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(id);
        if(emprestimoOptional.isPresent()) emprestimoRepository.save(emprestimo);
    }

    @Override
    public void deletar(Long id) {
        emprestimoRepository.deleteById(id);
    }
}
