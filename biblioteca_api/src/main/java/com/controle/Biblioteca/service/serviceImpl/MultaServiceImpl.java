package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.model.Multa;
import com.controle.Biblioteca.repository.MultaRepository;
import com.controle.Biblioteca.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultaServiceImpl implements MultaService {

    @Autowired
    private MultaRepository multaRepository;

    @Override
    public Iterable<Multa> buscarTodos() {
        return multaRepository.findAll();
    }

    @Override
    public Multa buscarPorEmprestimo(Emprestimo emprestimo) {
        Optional<Multa> multaOptional = multaRepository.findByEmprestimo(emprestimo);
        return multaOptional.orElse(null);
    }

    @Override
    public Multa buscarPorId(Long id) {
        Optional<Multa> multaOptional = multaRepository.findById(id);
        return multaOptional.orElse(null);
    }

    @Override
    public void inserir(Multa multa) {
        multa.setId(null);
        multaRepository.save(multa);
    }

    @Override
    public void atualizar(Long id, Multa multa) {
        Optional<Multa> multaOptional = multaRepository.findById(id);
        if(multaOptional.isPresent()) multaRepository.save(multa);
    }

    @Override
    public void deletar(Long id) {
        multaRepository.deleteById(id);
    }
}
