package br.com.biblioteca.apresentacao;

import br.com.biblioteca.dao.JPAUtil;
import br.com.biblioteca.entidades.*;
import br.com.biblioteca.negocio.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoView {

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;
    private final UsuarioService usuarioService;
    private final JogoService jogoService;
    
    private final UsuarioView usuarioView;
    private final JogoView jogoView;

    public PedidoView(PedidoService pedidoService, ItemPedidoService itemPedidoService, 
                      UsuarioService usuarioService, JogoService jogoService, 
                      UsuarioView usuarioView, JogoView jogoView) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
        this.usuarioService = usuarioService;
        this.jogoService = jogoService;
        this.usuarioView = usuarioView;
        this.jogoView = jogoView;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR PEDIDOS ---");
            System.out.println("1: Criar novo pedido");
            System.out.println("2: Listar pedidos");
            System.out.println("3: Remover pedido");
            System.out.println("4: Adicionar item a um pedido");
            System.out.println("5: Remover item de um pedido");
            System.out.println("0: Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = ConsoleUtils.lerOpcao();

            try {
                switch (opcao) {
                    case 1: cadastrarPedido(); break;
                    case 2: listarPedidos(); break;
                    case 3: removerPedido(); break;
                    case 4: adicionarItemAoPedido(); break;
                    case 5: removerItemDoPedido(); break;
                }
            } catch (Exception e) {
                System.err.println("\n[ERRO] " + e.getMessage());
            }
        }
    }

    private void cadastrarPedido() throws Exception {
        System.out.println("Selecione o usuário para o pedido:");
        usuarioView.listarUsuarios();
        
        System.out.print("Digite o ID do Usuário: ");
        Long usuarioId = ConsoleUtils.lerLong();
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDate.now());
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        while (true) {
            System.out.println("\nAdicionando itens ao pedido (digite 0 para finalizar):");
            jogoView.listarJogos();
            
            System.out.print("Digite o ID do jogo para adicionar (ou 0 para parar): ");
            Long jogoId = ConsoleUtils.lerLong();
            if (jogoId == 0) break;

            Jogo jogo = jogoService.buscarPorId(jogoId);
            if (jogo == null) {
                System.out.println("Jogo não encontrado.");
                continue;
            }

            System.out.print("Digite a quantidade: ");
            int quantidade = ConsoleUtils.lerOpcao();
            if (quantidade <= 0) {
                System.out.println("Quantidade deve ser positiva.");
                continue;
            }

            ItemPedido item = new ItemPedido();
            item.setJogo(jogo);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(jogo.getPreco());
            item.setPedido(pedido);

            itensDoPedido.add(item);
            System.out.println("Jogo '" + jogo.getTitulo() + "' adicionado ao carrinho.");
        }

        if (itensDoPedido.isEmpty()) {
            System.out.println("Pedido cancelado pois nenhum item foi adicionado.");
            return;
        }

        pedido.setItens(itensDoPedido);
        pedidoService.salvar(pedido);
        System.out.println("Pedido salvo com sucesso! ID: " + pedido.getId());
    }

    private void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de pedidos ---");
        for (Pedido p : pedidos) {
            System.out.println("---------------------------------");
            System.out.printf("ID Pedido: %d | Data: %s | Usuário: %s (ID: %d)\n",
                p.getId(), p.getDataPedido().toString(), p.getUsuario().getNome(), p.getUsuario().getId());
            
            System.out.println("Itens do pedido:");
            double totalPedido = 0;
            for (ItemPedido item : p.getItens()) {
                System.out.printf("  - (Item ID: %d) Jogo: %s | Qtd: %d | Preço Un.: R$%.2f\n",
                    item.getId(), item.getJogo().getTitulo(), item.getQuantidade(), item.getPrecoUnitario());
                totalPedido += item.getPrecoUnitario() * item.getQuantidade();
            }
            System.out.printf("Total do pedido: R$%.2f\n", totalPedido);
        }
        System.out.println("---------------------------------");
    }

    private void removerPedido() throws Exception {
        System.out.print("Digite o ID do pedido para remover: ");
        Long id = ConsoleUtils.lerLong();
        
        Pedido p = pedidoService.buscarPorId(id);
        if (p == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
        
        pedidoService.remover(id);
        System.out.println("Pedido (e seus itens) removido com sucesso!");
    }

    private void adicionarItemAoPedido() throws Exception {
        System.out.println("Selecione o pedido que deseja modificar:");
        listarPedidos();
        
        System.out.print("Digite o ID do pedido para modificar: ");
        Long pedidoId = ConsoleUtils.lerLong();
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
        
        System.out.println("Selecione o jogo para adicionar:");
        jogoView.listarJogos();

        System.out.print("Digite o ID do Jogo: ");
        Long jogoId = ConsoleUtils.lerLong();
        Jogo jogo = jogoService.buscarPorId(jogoId);
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int quantidade = ConsoleUtils.lerOpcao();
        if (quantidade <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        ItemPedido item = new ItemPedido();
        item.setJogo(jogo);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(jogo.getPreco());
        item.setPedido(pedido);

        itemPedidoService.salvar(item);

        JPAUtil.getEntityManagerFactory().getCache().evict(Pedido.class, pedido.getId());

        System.out.println("Item adicionado com sucesso ao pedido " + pedido.getId());
    }

    private void removerItemDoPedido() throws Exception {
        System.out.println("Selecione o pedido que deseja modificar:");
        listarPedidos();
        
        System.out.print("Digite o ID do pedido para modificar: ");
        Long pedidoId = ConsoleUtils.lerLong();
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("Itens atuais no Pedido " + pedido.getId() + ":");

        Pedido pedidoAtualizado = pedidoService.buscarPorId(pedido.getId());
        List<ItemPedido> itens = pedidoAtualizado.getItens();

        if (itens.isEmpty()) {
            System.out.println("Este pedido não possui itens para remover.");
            return;
        }

        for (ItemPedido item : itens) {
            System.out.printf("  - (Item ID: %d) Jogo: %s | Qtd: %d\n",
                item.getId(), item.getJogo().getTitulo(), item.getQuantidade());
        }

        System.out.print("Digite o ID do item para remover: ");
        Long itemId = ConsoleUtils.lerLong();

        boolean itemEncontrado = itens.stream().anyMatch(item -> item.getId().equals(itemId));

        if (itemEncontrado) {
            itemPedidoService.remover(itemId);

            JPAUtil.getEntityManagerFactory().getCache().evict(Pedido.class, pedido.getId());
            
            System.out.println("Item removido com sucesso!");
        } else {
            System.out.println("ID do item não encontrado neste pedido.");
        }
    }
}
