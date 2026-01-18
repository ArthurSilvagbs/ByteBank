package controller;

import dao.ContaDAOJPA;
import dao.MovimentacaoDAOJPA;
import jakarta.persistence.EntityManager;
import model.Conta;
import model.Movimentacao;
import util.JPAUtil;

import java.math.BigDecimal;
import java.util.List;

public class ContaController {

    private final static MovimentacoController movimentacaoController = new MovimentacoController();

    public void realizarDeposito(Conta conta, BigDecimal valorDeposito) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacaoDAOJPA movDao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();

            BigDecimal saldoAtual = conta.getSaldo();
            conta.setSaldo(saldoAtual.add(valorDeposito));

            Movimentacao movimentacao = new Movimentacao(conta, valorDeposito, "Deposito");

            contaDao.atualizar(conta);
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
            movDao.fechar();
        }

    }

    public void realizarSaque(Conta conta, BigDecimal valorSaque) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacaoDAOJPA movDao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();

            BigDecimal saldoAtual = conta.getSaldo();
            conta.setSaldo(saldoAtual.subtract(valorSaque));

            Movimentacao movimentacao = new Movimentacao(conta, valorSaque, "Saque");

            contaDao.atualizar(conta);
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
            movDao.fechar();
        }

    }

    public void realizarPix(Conta contaOrigem, Conta contaDestino, BigDecimal valorTransferencia) {

    }

    //==================================

    //vincular conta ao cliente

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

    public void atualizarSaldo(BigDecimal saldo) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA dao = new ContaDAOJPA(em);
        Conta conta = new Conta();

        conta.setSaldo(saldo);

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
