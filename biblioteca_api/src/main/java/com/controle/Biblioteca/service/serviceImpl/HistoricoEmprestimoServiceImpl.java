package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.repository.HistoricoEmprestimoRepository;
import com.controle.Biblioteca.service.HistoricoEmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoricoEmprestimoServiceImpl implements HistoricoEmprestimoService {

    @Autowired
    private HistoricoEmprestimoRepository historicoEmprestimoRepository;


    @Override
    public Iterable<HistoricoEmprestimo> buscarTodos() {
        return historicoEmprestimoRepository.findAll();
    }

    @Override
    public HistoricoEmprestimo buscarPorCliente(Cliente cliente) {
        Optional<HistoricoEmprestimo> histEmprestimoOptional = historicoEmprestimoRepository.findByCliente(cliente);
        return histEmprestimoOptional.orElse(null);
    }

    @Override
    public HistoricoEmprestimo buscarPorId(Long id) {
        Optional<HistoricoEmprestimo> histEmprestimoOptional = historicoEmprestimoRepository.findById(id);
        return histEmprestimoOptional.orElse(null);
    }

    @Override
    public void inserir(HistoricoEmprestimo historicoEmprestimo) {
        historicoEmprestimo.setId(null);
        historicoEmprestimoRepository.save(historicoEmprestimo);
    }

    @Override
    public void atualizar(Long id, HistoricoEmprestimo historicoEmprestimo) {
        Optional<HistoricoEmprestimo> historicoEmprestimoOptional = historicoEmprestimoRepository.findById(id);
        if(historicoEmprestimoOptional.isPresent()) historicoEmprestimoRepository.save(historicoEmprestimo);
    }

    @Override
    public void deletar(Long id) {
        historicoEmprestimoRepository.deleteById(id);
    }
}
