# 🏦 ByteBank (Versão Console & JPA)

Projeto desenvolvido para estudo aprofundado da linguagem Java e da especificação **JPA (Jakarta Persistence API)**.

O objetivo deste projeto não é ser um produto final, mas sim um laboratório para aplicar conceitos de Orientação a Objetos, gerenciamento manual de transações e manipulação de banco de dados relacional sem a abstração de frameworks web.

## Objetivo de Estudo

Este projeto foca no entendimento "under the hood" (por baixo do capô) de como o Java se comunica com o banco de dados, implementando manualmente padrões que frameworks como Spring Data costumam esconder:

- **Ciclo de Vida do Entity Manager:** Abertura, fechamento e gerenciamento de contexto de persistência.
- **Transações Manuais:** Controle explícito de `begin`, `commit` e `rollback` para garantir a atomicidade das operações bancárias.
- **Padrão DAO (Data Access Object):** Isolamento da camada de acesso a dados usando implementação manual.
- **JPQL (Java Persistence Query Language):** Escrita de consultas orientadas a objetos.

## Tecnologias Utilizadas

- **Java 21**: Linguagem base.
- **JPA / Hibernate**: Para Mapeamento Objeto-Relacional (ORM).
- **MySQL**: Banco de dados relacional.
- **Maven**: Gerenciamento de dependências.
- **Interface via Console**: Uso da classe `Scanner` para interação via terminal.

## Estrutura do Projeto

O código está organizado em camadas lógicas para separar a interação com o usuário da regra de negócio e da persistência:

```text
src/main/java
├── view
│   └── Menus.java          # Interface CLI (Scanner) que captura inputs do usuário.
├── controller
│   └── [Entidade]Controller.java # Gerencia regras de negócio e orquestra as transações (abrir/fechar EntityManager).
├── dao
│   └── [Entidade]DAOJPA.java     # Executa operações de banco (persist, merge, find) e consultas JPQL.
├── model
│   ├── entities            # Classes POJO anotadas com @Entity (Cliente, Conta, Movimentacao).
│   └── JPAUtil.java        # Singleton para fabricação do EntityManager.
```

## Funcionalidades Implementadas

O sistema roda em um loop infinito no terminal (`Menus.java`), oferecendo as seguintes opções:

1. **Cadastrar Cliente:** Inserção de dados com persistência no banco.
2. **Abrir Conta:** Vinculação de uma nova conta a um cliente existente (`@OneToMany`).
3. **Depósito e Saque:** Atualização de saldo com registro de movimentação.
4. **Transferência (Pix):**
   * Lógica crítica onde um saque e um depósito ocorrem na mesma transação.
   * Se falhar no meio, o `rollback` desfaz a operação inteira.
5. **Consultar Extrato:** Consulta JPQL que busca as movimentações de uma conta específica.

## Como rodar

1. No terminal do IntelliJ ou GitBash, clone o repositório:
   ```bash
   git clone https://github.com/ArthurSilvagbs/ByteBank.git
   ```



