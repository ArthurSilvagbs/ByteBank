package dao;

import jakarta.persistence.EntityManager;
import model.Cliente;
import model.Conta;

import java.util.List;

public record ClienteDAOJPA(EntityManager em) implements ClienteDAO {

    @Override
    public void inserir(Cliente cliente) {
        this.em.persist(cliente);
    }

    @Override
    public void excluir(Cliente cliente) {
        this.em.remove(cliente);
    }

    @Override
    public void atualizar(Cliente cliente) {
        this.em.merge(cliente);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return em.find(Cliente.class, cpf);
    }

    @Override
    public List<Cliente> obterTodos() {
        String jpql = "SELECT c FROM Cliente c";
        return em.createQuery(jpql, Cliente.class).getResultList();
    }

    @Override
    public List<Conta> obterContasCliente(Cliente cliente) {
        String jpql = "SELECT c FROM Conta c WHERE c.cliente.cpf = :cpfCliente";
        return em.createQuery(jpql, Conta.class)
                .setParameter("cpfCliente", cliente.getCpf())
                .getResultList();
    }

    @Override
    public void fechar() {
        if (this.em != null && this.em.isOpen()) {
            this.em.close();
        }
    }
}
