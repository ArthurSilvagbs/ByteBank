package model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", nullable = false)
    Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id", nullable = false)
    Conta contaDestino;

    @Column(scale = 11, precision = 2, nullable = false)
    BigDecimal valor;

    public Movimentacao() {
    }

    public Movimentacao(Long id, Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
