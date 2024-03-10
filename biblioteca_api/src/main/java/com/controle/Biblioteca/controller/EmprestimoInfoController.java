package com.controle.Biblioteca.controller;

import com.controle.Biblioteca.service.EmprestimoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimo-info")
public class EmprestimoInfoController {

    @Autowired
    private EmprestimoInfoService emprestimoInfoService;

    @GetMapping
    public ResponseEntity<List<Object[]>> obterTodosOsEmprestimosInfo() {
        List<Object[]> emprestimoInfo = emprestimoInfoService.obterTodosOsEmprestimosinfo();
        return ResponseEntity.ok(emprestimoInfo);
    }
}
