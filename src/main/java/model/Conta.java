package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 5, nullable = false, unique = true)
    private Long numeroConta;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal saldo = new BigDecimal(BigInteger.ZERO);

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "contaOrigem", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Movimentacao> entradas = new ArrayList<>();

    @OneToMany(mappedBy = "contaDestino", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Movimentacao> saidas = new ArrayList<>();

    public Conta() {
    }

    public Conta(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
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

    public List<Movimentacao> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Movimentacao> entradas) {
        this.entradas = entradas;
    }

    public List<Movimentacao> getSaidas() {
        return saidas;
    }

    public void setSaidas(List<Movimentacao> saidas) {
        this.saidas = saidas;
    }

    @Override
    public String toString() {
        return String.format("| Nº CONTA: %d | CPF TITULAR: %s | SALDO: %.2f |",this.numeroConta, this.cliente.getCpf(), this.saldo);
    }
}
