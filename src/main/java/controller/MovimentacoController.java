package controller;

import dao.MovimentacaoDAOJPA;
import jakarta.persistence.EntityManager;
import model.Conta;
import model.Movimentacao;
import util.JPAUtil;

import java.util.List;

public class MovimentacoController {

    public void criarMovimentacao(Movimentacao movimentacao) {

        EntityManager em = JPAUtil.getEntityManager();
        MovimentacaoDAOJPA dao = new MovimentacaoDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.inserir(movimentacao);
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


    public Movimentacao buscarMovimentacaoPorId(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        MovimentacaoDAOJPA dao = new MovimentacaoDAOJPA(em);

        try {
            return dao.buscarPorId(id);
        } finally {
            dao.fechar();
        }

    }

    public List<Movimentacao> obterMovimentacaoPorConta(Conta conta) {
        EntityManager em = JPAUtil.getEntityManager();
        MovimentacaoDAOJPA dao = new MovimentacaoDAOJPA(em);

        try {
            List<Movimentacao> movimentacoes = dao.obterTodos(conta);
            movimentacoes.size();
            return movimentacoes;
        } finally {
            em.close();
        }
    }


}
