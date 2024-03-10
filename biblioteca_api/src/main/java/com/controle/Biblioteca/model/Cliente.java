package com.controle.Biblioteca.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "datanascimento")
    private LocalDate dataNascimento;
    @Column(name = "datacadastro")
    private LocalDate dataCadastro;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String genero;
    private String status;

    public Cliente() {
        this.id = null;
        this.dataNascimento = null;
        this.dataCadastro = LocalDate.now();
        this.nome = "";
        this.cpf = "";
        this.telefone = "";
        this.email = "";
        this.genero = "";
        this.status = "R";
    }

    public Cliente(LocalDate dataNascimento, String nome, String cpf, String telefone,
                   String email, String genero) {
        this.id = null;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = LocalDate.now();
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.genero = genero;
        this.status = "R";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
