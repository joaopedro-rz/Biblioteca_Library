package com.controle.Biblioteca.controller;

import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.service.ClienteService;
import com.controle.Biblioteca.service.HistoricoEmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("historico/emprestimo")
public class HistoricoEmprestimoController {

    @Autowired
    private HistoricoEmprestimoService historicoEmprestimoService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<HistoricoEmprestimo>> buscarTodos() {
        return ResponseEntity.ok(historicoEmprestimoService.buscarTodos());
    }
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<HistoricoEmprestimo> buscarPorCliente(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);
        if(cliente != null) return ResponseEntity.ok(historicoEmprestimoService.buscarPorCliente(cliente));
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<HistoricoEmprestimo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historicoEmprestimoService.buscarPorId(id));
    }
    @PostMapping("/create")
    public ResponseEntity<HistoricoEmprestimo> inserir(@RequestBody HistoricoEmprestimo historicoEmprestimo) {
        historicoEmprestimoService.inserir(historicoEmprestimo);
        return ResponseEntity.ok(historicoEmprestimo);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HistoricoEmprestimo> atualizar(@PathVariable Long id, @RequestBody HistoricoEmprestimo historicoEmprestimo) {
        historicoEmprestimoService.atualizar(id, historicoEmprestimo);
        return ResponseEntity.ok(historicoEmprestimo);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HistoricoEmprestimo> deletar(@PathVariable Long id) {
        historicoEmprestimoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
