package br.com.biblioteca;

import br.com.biblioteca.dao.JPAUtil;
import br.com.biblioteca.negocio.*;
import br.com.biblioteca.apresentacao.*;

import br.com.biblioteca.apresentacao.UsuarioView;

public class Main {

    public static void main(String[] args) {
        // instanciar todos os serviços (camada de negócio)
        UsuarioService usuarioService = new UsuarioService();
        DesenvolvedoraService devService = new DesenvolvedoraService();
        GeneroService generoService = new GeneroService();
        PlataformaService plataformaService = new PlataformaService();
        JogoService jogoService = new JogoService();
        PedidoService pedidoService = new PedidoService();
        ItemPedidoService itemPedidoService = new ItemPedidoService();

        // instanciar todas as views (camada de apresentação)
        UsuarioView usuarioView = new UsuarioView(usuarioService);
        DesenvolvedoraView devView = new DesenvolvedoraView(devService);
        GeneroView generoView = new GeneroView(generoService);
        PlataformaView plataformaView = new PlataformaView(plataformaService);
        JogoView jogoView = new JogoView(jogoService, devService, generoService, plataformaService);
        PedidoView pedidoView = new PedidoView(pedidoService, itemPedidoService, usuarioService, jogoService, usuarioView, jogoView);

        menuPrincipal(usuarioView, devView, generoView, plataformaView, jogoView, pedidoView);
        
        ConsoleUtils.fecharScanner();
        JPAUtil.close();
        System.out.println("Sistema encerrado.");
    }

    private static void menuPrincipal(UsuarioView usuarioView, DesenvolvedoraView devView, GeneroView generoView, PlataformaView plataformaView, JogoView jogoView, PedidoView pedidoView) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- BIBLIOTECA DE JOGOS (MENU PRINCIPAL) ---");
            System.out.println("1: Gerenciar usuários");
            System.out.println("2: Gerenciar desenvolvedoras");
            System.out.println("3: Gerenciar gêneros");
            System.out.println("4: Gerenciar plataformas");
            System.out.println("5: Gerenciar jogos");
            System.out.println("6: Gerenciar pedidos");
            System.out.println("0: Sair");
            System.out.print("Escolha uma opção: ");

            opcao = ConsoleUtils.lerOpcao();

            switch (opcao) {
                case 1: usuarioView.exibirMenu(); break;
                case 2: devView.exibirMenu(); break;
                case 3: generoView.exibirMenu(); break;
                case 4: plataformaView.exibirMenu(); break;
                case 5: jogoView.exibirMenu(); break;
                case 6: pedidoView.exibirMenu(); break;
                case 0: break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}