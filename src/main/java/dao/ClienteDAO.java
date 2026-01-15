package dao;

import model.Cliente;

import java.util.List;

public interface ClienteDAO {

    void inserir(Cliente cliente);
    void excluir(Cliente cliente);
    void atualizar(Cliente cliente);
    Cliente buscarPorCpf(String cpf);
    List<Cliente> obterTodos();
    void fechar();

}
