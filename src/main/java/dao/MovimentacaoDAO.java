package dao;

import model.Conta;
import model.Movimentacao;

import java.util.List;

public interface MovimentacaoDAO {

    void inserir(Movimentacao movimentacao);
    Movimentacao buscarPorId(Long id);
    List<Movimentacao> obterTodos(Conta conta);
    void fechar();

}
