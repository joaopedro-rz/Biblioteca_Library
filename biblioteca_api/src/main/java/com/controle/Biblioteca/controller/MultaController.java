package com.controle.Biblioteca.controller;


import com.controle.Biblioteca.model.Emprestimo;
import com.controle.Biblioteca.model.Multa;
import com.controle.Biblioteca.service.EmprestimoService;
import com.controle.Biblioteca.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("multa")
public class MultaController {

    @Autowired
    private MultaService multaService;
    @Autowired
    private EmprestimoService emprestimoService;
    @GetMapping("/all")
    public ResponseEntity<Iterable<Multa>> buscarTodos() {
        return ResponseEntity.ok(multaService.buscarTodos());
    }
    @GetMapping("/emprestimo/{idEmprestimo}")
    public ResponseEntity<Multa> buscarPorEmprestimo(@PathVariable Long idEmprestimo) {
        Emprestimo emprestimo = emprestimoService.buscarPorId(idEmprestimo);
        if(emprestimo != null)
            return ResponseEntity.ok(multaService.buscarPorEmprestimo(emprestimo));
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Multa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.buscarPorId(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Multa> inserir(@RequestBody Multa multa) {
        multaService.inserir(multa);
        return ResponseEntity.ok(multa);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Multa> atualizar(@PathVariable Long id, @RequestBody Multa multa) {
        multaService.atualizar(id, multa);
        return ResponseEntity.ok(multa);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Multa> deletar(@PathVariable Long id) {
        multaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
