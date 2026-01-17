package dao;

import jakarta.persistence.EntityManager;
import model.Movimentacao;

import java.util.List;

public class MovimentacaoDAOJPA implements MovimentacaoDAO{

    private final EntityManager em;

    public MovimentacaoDAOJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void inserir(Movimentacao movimentacao) {
        this.em.persist(movimentacao);
    }

    @Override
    public Movimentacao buscarPorId(Long id) {
        return em.find(Movimentacao.class, id);
    }

    @Override
    public List<Movimentacao> obterTodos() {
        String jpql = "SELECT m FROM Movimentacao m WHERE m.conta.numeroConta = :numeroConta";
        return em.createQuery(jpql, Movimentacao.class).getResultList();
    }

    @Override
    public void fechar() {
        if (this.em != null && this.em.isOpen()) {
            this.em.close();
        }
    }
}
