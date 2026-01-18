package model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    @Column(scale = 2, precision = 11, nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String tipoMovimentacao;

    public Movimentacao() {
    }

    public Movimentacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.tipoMovimentacao = "Transferência";

        contaOrigem.getSaidas().add(this);
        contaDestino.getEntradas().add(this);
    }

    public Movimentacao(Conta conta, BigDecimal valor, String tipo) {
        this.valor = valor;
        this.tipoMovimentacao = tipo;

        if (tipo.equalsIgnoreCase("SAQUE")) {
            this.contaOrigem = conta;
            conta.getSaidas().add(this);
        } else {
            this.contaDestino = conta;
            conta.getEntradas().add(this);
        }

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

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

}
