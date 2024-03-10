package com.controle.Biblioteca.controller;

import com.controle.Biblioteca.model.HistoricoEmprestimo;
import com.controle.Biblioteca.model.HistoricoMulta;
import com.controle.Biblioteca.service.HistoricoEmprestimoService;
import com.controle.Biblioteca.service.HistoricoMultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("historico/multa")
public class HistoricoMultaController {
    @Autowired
    private HistoricoMultaService  historicoMultaService;
    @Autowired
    private HistoricoEmprestimoService historicoEmprestimoService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<HistoricoMulta>> buscarTodos() {
        return ResponseEntity.ok(historicoMultaService.buscarTodos());
    }
    @GetMapping("/emprestimo/{idEmprestimo}")
    public ResponseEntity<HistoricoMulta> buscarPorEmprestimo(@PathVariable Long idHistoricoEmprestimo) {
        HistoricoEmprestimo historicoEmprestimo = historicoEmprestimoService.buscarPorId(idHistoricoEmprestimo);
        if(historicoEmprestimo != null)
            return ResponseEntity.ok(historicoMultaService.buscarPorHistoricoEmprestimo(historicoEmprestimo));
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<HistoricoMulta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historicoMultaService.buscarPorId(id));
    }
    @PostMapping("/create")
    public ResponseEntity<HistoricoMulta> inserir(@RequestBody HistoricoMulta historicoMulta) {
        historicoMultaService.inserir(historicoMulta);
        return ResponseEntity.ok(historicoMulta);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HistoricoMulta> atualizar(@PathVariable Long id, @RequestBody HistoricoMulta historicoMulta) {
        historicoMultaService.atualizar(id, historicoMulta);
        return ResponseEntity.ok(historicoMulta);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HistoricoMulta> deletar(@PathVariable Long id) {
        historicoMultaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}

