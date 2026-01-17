package controller;

import dao.ContaDAOJPA;
import dao.MovimentacaoDAOJPA;
import jakarta.persistence.EntityManager;
import model.Movimentacao;
import util.JPAUtil;

import java.math.BigDecimal;
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

    public List<Movimentacao> obterMovimentacaoPorConta() {

        EntityManager em = JPAUtil.getEntityManager();
        MovimentacaoDAOJPA dao = new MovimentacaoDAOJPA(em);

        try {
            return dao.obterTodos();
        } finally {
            dao.fechar();
        }

    }
}
