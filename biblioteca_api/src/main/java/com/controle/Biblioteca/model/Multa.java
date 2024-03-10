package com.controle.Biblioteca.model;

import jakarta.persistence.*;

@Entity
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diasatrasado")
    private int diasAtraso;
    @Column(name = "valordia")
    private float valorDia;
    @OneToOne
    @JoinColumn(name = "idemprestimo")
    private Emprestimo emprestimo;

    public Multa(int diasAtraso, float valorDia, Emprestimo emprestimo) {
        this.id = null;
        this.diasAtraso = diasAtraso;
        this.valorDia = valorDia;
        this.emprestimo = emprestimo;
    }
    public Multa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public float getValorDia() {
        return valorDia;
    }

    public void setValorDia(float valorDia) {
        this.valorDia = valorDia;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
}
