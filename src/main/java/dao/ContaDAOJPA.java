package dao;

import jakarta.persistence.EntityManager;
import model.Conta;

import java.util.List;

public class ContaDAOJPA implements ContaDAO{

    private final EntityManager em;

    public ContaDAOJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void inserir(Conta conta) {
        this.em.persist(conta);
    }

    @Override
    public void excluir(Conta conta) {
        this.em.remove(conta);
    }

    @Override
    public void atualizar(Conta conta) {
        this.em.merge(conta);
    }

    @Override
    public Conta buscarPorNumero(Long numeroConta) {
        return em.find(Conta.class, numeroConta);
    }

    @Override
    public List<Conta> obterTodos() {
        String jpql = "String jpql = \"SELECT c FROM Cliente c JOIN FETCH c.contas WHERE c.id = :id\";";
        return em.createQuery(jpql, Conta.class).getResultList();
    }

    @Override
    public void fechar() {
        if (this.em != null && this.em.isOpen()) {
            this.em.close();
        }
    }
}
