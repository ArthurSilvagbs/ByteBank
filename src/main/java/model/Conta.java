package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Conta {

    @Id
    @Column(length = 5, nullable = false, unique = true)
    Integer numeroConta;

    @Column(nullable = false, precision = 11, scale = 2)
    BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", nullable = false)
    Cliente cliente;

    @OneToMany
    @JoinColumn(name = "movimentação_id")
    List<Movimentacao> movimentacoes;

    public Conta() {
    }

    public Conta(Integer numeroConta, BigDecimal saldo, Cliente cliente) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
