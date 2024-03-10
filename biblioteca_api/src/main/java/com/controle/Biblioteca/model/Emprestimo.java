package com.controle.Biblioteca.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dataemprestimo")
    private LocalDate dataEmprestimo;
    @Column(name = "dataentrega")
    private LocalDate dataEntrega;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idlivro")
    private Livro livro;
    private String status;

    @PrePersist
    public void prePersist() {
        this.dataEntrega = LocalDate.now().plusDays(10);
    }
    public Emprestimo(LocalDate dataEntrega, Cliente cliente, Livro livro) {
        this.id = null;
        this.dataEmprestimo = LocalDate.now();
        this.dataEntrega = dataEntrega;
        this.cliente = cliente;
        this.livro = livro;
        this.status = "R";
    }

    public Emprestimo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

