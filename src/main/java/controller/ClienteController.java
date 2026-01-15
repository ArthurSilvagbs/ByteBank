package controller;

import dao.ClienteDAOJPA;
import jakarta.persistence.EntityManager;
import model.Cliente;
import model.Conta;
import util.JPAUtil;

import java.util.List;

public class ClienteController {

    public void cadastrarCliente(Cliente cliente) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.inserir(cliente);
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

    public void removerCliente(Cliente cliente) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.excluir(cliente);
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

    public void atualizarDadosCliente(Cliente cliente) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            em.getTransaction().begin();
            dao.atualizar(cliente);
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

    public Cliente buscarClientePorCPF(String cpf) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            return dao.buscarPorCpf(cpf);
        } finally {
            dao.fechar();
        }

    }

    public List<Cliente> obterTodosClientes() {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            return dao.obterTodos();
        } finally {
            dao.fechar();
        }

    }

    public List<Conta> obterContasCliente(Cliente cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAOJPA dao = new ClienteDAOJPA(em);

        try {
            return dao.obterContasCliente(cliente);
        } finally {
            dao.fechar();
        }
    }

}
