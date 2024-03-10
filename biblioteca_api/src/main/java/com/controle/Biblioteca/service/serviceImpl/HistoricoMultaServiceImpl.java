package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.model.HistoricoMulta;
import com.controle.Biblioteca.repository.HistoricoMultaRepository;
import com.controle.Biblioteca.service.HistoricoMultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoricoMultaServiceImpl implements HistoricoMultaService {

    @Autowired
    private HistoricoMultaRepository historicoMultaRepository;

    @Override
    public Iterable<HistoricoMulta> buscarTodos() {
        return historicoMultaRepository.findAll();
    }

    @Override
    public HistoricoMulta buscarPorHistoricoEmprestimo(HistoricoEmprestimo historicoEmprestimo) {
        Optional<HistoricoMulta> historicoMultaOptional = historicoMultaRepository.findByHistoricoEmprestimo(historicoEmprestimo);
        return historicoMultaOptional.orElse(null);
    }

    @Override
    public HistoricoMulta buscarPorId(Long id) {
        Optional<HistoricoMulta> historicoMultaOptinal = historicoMultaRepository.findById(id);
        return historicoMultaOptinal.orElse(null);
    }

    @Override
    public void inserir(HistoricoMulta historicoMulta) {
        historicoMulta.setId(null);
        historicoMultaRepository.save(historicoMulta);
    }

    @Override
    public void atualizar(Long id, HistoricoMulta historicoMulta) {
        Optional<HistoricoMulta> historicoMultaOptional = historicoMultaRepository.findById(id);
        if(historicoMultaOptional.isPresent()) historicoMultaRepository.save(historicoMulta);
    }

    @Override
    public void deletar(Long id) {
        historicoMultaRepository.deleteById(id);
    }
}
