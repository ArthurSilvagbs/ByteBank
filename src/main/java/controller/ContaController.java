package controller;

import dao.ClienteDAOJPA;
import dao.ContaDAOJPA;
import jakarta.persistence.EntityManager;
import model.Cliente;
import model.Conta;
import util.JPAUtil;

import java.math.BigDecimal;

public class ContaController {

    //realizar deposito
    //realizar saque
    //fazer transferencia

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

}
