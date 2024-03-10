package com.controle.Biblioteca.service.serviceImpl;

import com.controle.Biblioteca.service.EmprestimoInfoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoInfoServiceImpl implements EmprestimoInfoService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Object[]> obterTodosOsEmprestimosinfo() {
        String query = "SELECT * FROM vw_emprestimo_info";
        return entityManager.createNativeQuery(query).getResultList();
    }
}
