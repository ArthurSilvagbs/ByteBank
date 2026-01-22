package view;

import controller.MovimentacoController;
import dao.ContaDAOJPA;
import jakarta.persistence.EntityManager;
import model.Conta;
import util.JPAUtil;

public class mainsteste {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        ContaDAOJPA contaDao = new ContaDAOJPA(em);
        MovimentacoController movDao = new MovimentacoController();

        Conta conta = em.find(Conta.class, 1);

        System.out.println(conta.getNumeroConta());
    }
}
