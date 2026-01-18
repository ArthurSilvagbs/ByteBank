package view;

import controller.ClienteController;
import controller.ContaController;
import controller.MovimentacoController;
import model.Cliente;
import model.Conta;
import model.Movimentacao;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

    private final static Scanner sc = new Scanner(System.in);
    private final static ClienteController clienteController = new ClienteController();
    private final static ContaController contaController = new ContaController();

    public static void exibirMenuPrincipal() {

        while (true) {

            System.out.println("""
                \n
                ======================================================
                ===================== BYTE BANK ======================
                ======================================================
                ***** Selecione uma operação que deseja realizar *****
                ======================================================""");
            System.out.print("""
                |\s\s         [ 1 ] - CADASTRAR NOVO CLIENTE          \s\s|
                |\s\s         [ 2 ] - ABRIR CONTA PARA CLINETE        \s\s|
                |\s\s         [ 3 ] - REALIZAR DEPOSITO               \s\s|
                |\s\s         [ 4 ] - REALIZAR SAQUE                  \s\s|
                |\s\s         [ 5 ] - FAZER TRANFERÊNCIA (PIX)        \s\s|
                |\s\s         [ 6 ] - CONSULTAR EXTRATO               \s\s|
                |\s\s         [ 7 ] - SAIR                            \s\s|
                
                Selecione um opção:\s""");
            int opcao = lerInteiro();

            switch (opcao) {
                case 1 -> exibirMenuCadastrarCliente();
                case 2 -> exibirMenuAbrirConta();
                case 3 -> exibirMenuDeposito();
                case 4 -> exibirMenuSaque();
                case 5 -> exibirMenuPix();
                case 6 -> exibirMenuExtrato();
                case 7 -> {
                    System.out.println("Finalizando o programa. Até logo!");
                    System.exit(0);
                }
                default -> System.out.println("Opção Inválida!");
            }

        }
    }

    private static void exibirMenuCadastrarCliente() {

        boolean ligado = true;

        do {

            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Nome completo: ");
            String nome = sc.nextLine();

            try {
                clienteController.cadastrarCliente(new Cliente(cpf, nome));
                System.out.println("Cadastrado!");
                ligado = false;
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar. Tente novamente.");
            }
        } while (ligado);
    }

    private static void exibirMenuAbrirConta() {

        boolean ligado = true;

        do {
            System.out.print("Insira o CPF do cliente titular da conta: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteController.buscarClientePorCPF(cpf);

            if (cliente != null) {
                try {
                    Conta conta = new Conta(cliente);
                    cliente.adicionarConta(conta);

                    contaController.criarConta(conta);

                    System.out.println("Conta aberta com sucesso!");
                    ligado = false;
                } catch (Exception e) {
                    System.out.println("Erro técnico: " + e.getMessage());
                }
            } else {
                int opcao = tentarNovamente();

                switch (opcao) {
                    case 1:
                        continue;
                    case 2:
                        ligado = false;
                        break;
                    default:
                        System.out.println("Opcao inváida!");
                        break;
                }
            }
        } while (ligado);
    }

    private static void exibirMenuDeposito() {

        boolean ligado = true;

        do {

            System.out.print("Número da conta: ");
            Long numeroConta = lerLong();

            Conta conta = contaController.buscarContaPorNumero(numeroConta);

            try {
                System.out.println("Resultado da pesquisa:\n" + conta);
            } catch (NullPointerException e) {
                System.out.println("Conta não encontrada. ");
                throw new RuntimeException(e);
            }

            int opcaoPesquisa = confirmacaoPesquisa();

            switch (opcaoPesquisa) {

                case 1:
                    if (conta != null) {
                        try {
                            System.out.print("Valor do deposito: R$ ");
                            BigDecimal valorDeposito = lerBigDecimal();

                            contaController.realizarDeposito(conta, valorDeposito);

                            ligado = false;
                        } catch (Exception e) {
                            System.out.println("Erro técnico: " + e.getMessage());
                        }
                    } else {
                        int opcao = 0;
                        boolean opcaoValida = false;

                        while (!opcaoValida) {
                            opcao = tentarNovamente();
                            if (opcao == 1 || opcao == 2) {
                                opcaoValida = true;
                            } else {
                                System.out.println("Opção inválida! Escolha 1 ou 2.");
                            }
                        }

                        if (opcao == 1) {
                            continue;
                        } else ligado = false;
                    }
                    break;

                case 2:
                    System.out.println("Tente novamente.");
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }


        } while (ligado);

    }

    private static void exibirMenuSaque() {

        boolean ligado = true;

        do {

            System.out.print("Número da conta: ");
            Long numeroConta = lerLong();

            Conta conta = contaController.buscarContaPorNumero(numeroConta);

            try {
                System.out.println("Resultado da pesquisa:\n" + conta);
            } catch (NullPointerException e) {
                System.out.println("Conta não encontrada. ");
                throw new RuntimeException(e);
            }

            int opcaoPesquisa = confirmacaoPesquisa();

            switch (opcaoPesquisa) {

                case 1:
                    if (conta != null) {
                        try {
                            System.out.print("Valor do saque: R$ ");
                            BigDecimal valorSaque = lerBigDecimal();

                            contaController.realizarSaque(conta, valorSaque);

                            ligado = false;
                        } catch (Exception e) {
                            System.out.println("Erro técnico: " + e.getMessage());
                        }
                    } else {
                        int opcao = 0;
                        boolean opcaoValida = false;

                        while (!opcaoValida) {
                            opcao = tentarNovamente();
                            if (opcao == 1 || opcao == 2) {
                                opcaoValida = true;
                            } else {
                                System.out.println("Opção inválida! Escolha 1 ou 2.");
                            }
                        }

                        if (opcao == 1) {
                            continue;
                        } else ligado = false;
                    }
                    break;

                case 2:
                    System.out.println("Tente novamente.");
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }


        } while (ligado);
    }

    private static void exibirMenuPix() {


    }

    private static void exibirMenuExtrato() {


    }





    private static int lerInteiro() {

        while(true) {
            try {
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.print("Erro! Digite um valor número válido: ");
                sc.nextLine();
            }
        }
    }

    private static Long lerLong() {

        while(true) {
            try {
                Long valor = sc.nextLong();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.print("Erro! Digite um valor número válido: ");
                sc.nextLine();
            }
        }
    }

    private static BigDecimal lerBigDecimal() {
        while(true) {
            try {
                BigDecimal valor = sc.nextBigDecimal();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.print("Erro! Digite um valor numérico (Ex: 1500,50): ");
                sc.nextLine();
            }
        }
    }

    private static String lerString() {
        while (true) {
            String entrada = sc.nextLine();
            if (!entrada.trim().isEmpty()) {
                return entrada;
            }
            System.out.print("Erro! O campo não pode ficar vazio. Digite novamente: ");
        }
    }

    private static int repetirOperacao() {
        System.out.println("""
                    REPETIR OPERAÇÃO?
                    
                    [ 1 ] SIM
                    [ 2 ] NÃO
                    
                    Selecione uma opção:\s""");
        return lerInteiro();
    }

    private static int tentarNovamente() {
        System.out.println("""
                    TENTAR NOVAMENTE?
                    
                    [ 1 ] SIM
                    [ 2 ] NÃO
                    
                    Selecione uma opção:\s""");
        return lerInteiro();
    }

    private static int confirmacaoPesquisa() {
        System.out.print("""
                Confirma o resultado da pesquisa?
                
                [ 1 ] SIM
                [ 2 ] NÃO
                
                Selecione uma opção:\s""");

        return lerInteiro();
    }


}
