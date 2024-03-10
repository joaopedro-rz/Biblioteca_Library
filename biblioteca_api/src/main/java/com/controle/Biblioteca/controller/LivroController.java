package com.controle.Biblioteca.controller;


import com.controle.Biblioteca.model.Livro;
import com.controle.Biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Livro>> buscarTodos() {
        return ResponseEntity.ok(livroService.buscarTodos());
    }
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Livro> buscarPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(livroService.buscarPorIsbn(isbn));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Livro> inserir(@RequestBody Livro livro) {
        livroService.inserir(livro);
        return ResponseEntity.ok(livro);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        livroService.atualizar(id, livro);
        return ResponseEntity.ok(livro);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Livro> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
