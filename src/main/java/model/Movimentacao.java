package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", nullable = true)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id", nullable = true)
    private Conta contaDestino;

    @Column(scale = 2, precision = 11, nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String tipoMovimentacao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Movimentacao() {
    }

    public Movimentacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.tipoMovimentacao = "Transferência";
        this.dataHora = LocalDateTime.now();

        contaOrigem.getSaidas().add(this);
        contaDestino.getEntradas().add(this);
    }

    public Movimentacao(Conta conta, BigDecimal valor, String tipo) {
        this.valor = valor;
        this.tipoMovimentacao = tipo;
        this.dataHora = LocalDateTime.now();

        if (tipo.equalsIgnoreCase("SAQUE")) {
            this.contaOrigem = conta;
            conta.getSaidas().add(this);
        } else if (tipo.equalsIgnoreCase("DEPOSITO")){
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

    public String getDataFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.dataHora.format(formatador);
    }

    public String toString() {
        if (this.tipoMovimentacao.equalsIgnoreCase("SAQUE") || this.tipoMovimentacao.equalsIgnoreCase("Deposito")) {
            Long num = (contaOrigem != null) ? contaOrigem.getNumeroConta() : contaDestino.getNumeroConta();
            return String.format("| ID: %d | CONTA: %d | DATA E HORA: %s | VALOR: R$ %.2f | TIPO: %s |",
                    this.id, num, this.getDataFormatada(), this.valor, this.tipoMovimentacao);
        } else {
            return String.format("| ID: %d | CONTA ORIGEM: %d | CONTA DESTINO: %d | DATA E HORA: %s | VALOR: R$ %.2f | TIPO: %s |",
                    this.id, this.contaOrigem.getNumeroConta(), this.contaDestino.getNumeroConta(),
                    this.getDataFormatada(), this.valor, this.tipoMovimentacao);
        }
    }
}
