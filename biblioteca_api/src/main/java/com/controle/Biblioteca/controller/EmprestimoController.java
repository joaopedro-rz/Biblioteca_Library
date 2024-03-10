package com.controle.Biblioteca.controller;


import com.controle.Biblioteca.model.Cliente;
import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.service.ClienteService;
import com.controle.Biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Emprestimo>> buscarTodos() {
        return ResponseEntity.ok(emprestimoService.buscarTodos());
    }
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Emprestimo> buscarPorCliente(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);
        if(cliente != null) return ResponseEntity.ok(emprestimoService.buscarPorCliente(cliente));
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Emprestimo> inserir(@RequestBody Emprestimo emprestimo) {
        emprestimoService.inserir(emprestimo);
        return ResponseEntity.ok(emprestimo);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Emprestimo> atualizar(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        emprestimoService.atualizar(id, emprestimo);
        return ResponseEntity.ok(emprestimo);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Emprestimo> deletar(@PathVariable Long id) {
        emprestimoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
