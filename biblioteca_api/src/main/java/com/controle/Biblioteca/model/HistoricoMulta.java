package com.controle.Biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "historicomulta")
public class HistoricoMulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diasatrasado")
    private int diasAtrasado;
    @Column(name = "valortotal")
    private Double valorTotal;
    @OneToOne
    @JoinColumn(name = "idhistoricoemprestimo")
    private HistoricoEmprestimo historicoEmprestimo;

    public HistoricoMulta() {
        this.id = null;
        this.diasAtrasado = 0;
        this.valorTotal = 0.0;
        this.historicoEmprestimo = new HistoricoEmprestimo();
    }

    public HistoricoMulta(int diasAtrasado, Double valorTotal, HistoricoEmprestimo historicoEmprestimo) {
        this.id = null;
        this.diasAtrasado = diasAtrasado;
        this.valorTotal = valorTotal;
        this.historicoEmprestimo = historicoEmprestimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDiasAtrasado() {
        return diasAtrasado;
    }

    public void setDiasAtrasado(int diasAtrasado) {
        this.diasAtrasado = diasAtrasado;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public HistoricoEmprestimo getHistoricoEmprestimo() {
        return historicoEmprestimo;
    }

    public void setHistoricoEmprestimo(HistoricoEmprestimo historicoEmprestimo) {
        this.historicoEmprestimo = historicoEmprestimo;
    }
}
