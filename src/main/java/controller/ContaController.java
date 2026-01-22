package controller;

import dao.ContaDAOJPA;
import dao.MovimentacaoDAOJPA;
import jakarta.persistence.EntityManager;
import model.Cliente;
import model.Conta;
import model.Movimentacao;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import util.JPAUtil;

import java.math.BigDecimal;
import java.util.List;

public class ContaController {

    public void realizarDeposito(Long numeroConta, BigDecimal valorDeposito) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacaoDAOJPA movDao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();

            Conta conta = em.find(Conta.class, numeroConta);
            BigDecimal saldoAtual = conta.getSaldo();
            conta.setSaldo(saldoAtual.add(valorDeposito));

            Movimentacao movimentacao = new Movimentacao(conta, valorDeposito, "Deposito");

            movDao.inserir(movimentacao);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
            contaDao.fechar();
        }

    }

    public void realizarSaque(Long numeroConta, BigDecimal valorSaque) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacaoDAOJPA movDao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();

            Conta conta = em.find(Conta.class, numeroConta);
            BigDecimal saldoAtual = conta.getSaldo();

            if (saldoAtual.compareTo(valorSaque) >= 0) {
                conta.setSaldo(saldoAtual.subtract(valorSaque));

                Movimentacao movimentacao = new Movimentacao(conta, valorSaque, "Saque");

                movDao.inserir(movimentacao);

                em.getTransaction().commit();
            } else {
                throw new RuntimeException("Saldo insuficiente!");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
            contaDao.fechar();
            movDao.fechar();
        }
    }

    public void realizarTransferencia(Long numeroContaOrigem, Long numeroContaDestino, BigDecimal valorTransferencia) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacaoDAOJPA movDao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();

            Conta contaOrigem = em.find(Conta.class, numeroContaOrigem);
            Conta contaDestino = em.find(Conta.class, numeroContaDestino);

            BigDecimal saldoAtualContaOrigem = contaOrigem.getSaldo();
            BigDecimal saldoAtualContaDestino = contaDestino.getSaldo();

            if (valorTransferencia.compareTo(BigDecimal.ZERO) > 0 && saldoAtualContaOrigem.compareTo(valorTransferencia) >= 0) {

                contaOrigem.setSaldo(saldoAtualContaOrigem.subtract(valorTransferencia));
                contaDestino.setSaldo(saldoAtualContaDestino.add(valorTransferencia));

                Movimentacao movimentacao = new Movimentacao(contaOrigem, contaDestino, valorTransferencia);

                movDao.inserir(movimentacao);

                em.getTransaction().commit();
            } else if (valorTransferencia.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("O valor da transferência deve ser maior que zero!");
            } else {
                throw new RuntimeException("Saldo insuficiente!");
            }


        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
            contaDao.fechar();
            movDao.fechar();
        }

    }

    public void criarConta(Conta conta) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.inserir(conta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            dao.fechar();
        }

    }

    public void encerrarConta(Conta conta) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.excluir(conta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            dao.fechar();
        }

    }

    public void atualizar(Conta conta) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.atualizar(conta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            dao.fechar();
        }

    }

    public Conta buscarContaPorNumero(Long numeroConta) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);

        try {
            return dao.buscarPorNumero(numeroConta);
        } finally {
            dao.fechar();
        }

    }

    public List<Conta> obterTodasContas() {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);

        try {
            return dao.obterTodos();
        } finally {
            dao.fechar();
        }

    }

}
