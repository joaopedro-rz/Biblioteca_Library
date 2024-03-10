package com.controle.Biblioteca.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface EmprestimoInfoService {

    List<Object[]> obterTodosOsEmprestimosinfo();
}
