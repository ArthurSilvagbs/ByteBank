package dao;

import model.Cliente;
import model.Conta;

import java.util.List;

public interface ContaDAO {

    void inserir(Conta conta);
    void excluir(Conta conta);
    void atualizar(Conta conta);
    Conta buscarPorNumero(Long numeroConta);
    List<Conta> obterTodos();
    void fechar();

}
