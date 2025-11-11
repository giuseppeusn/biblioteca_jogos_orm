package br.com.biblioteca.apresentacao;


import br.com.biblioteca.entidades.*;
import br.com.biblioteca.negocio.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JogoView {

    private final JogoService jogoService;
    private final DesenvolvedoraService devService;
    private final GeneroService generoService;
    private final PlataformaService plataformaService;

    public JogoView(JogoService jogoService, DesenvolvedoraService devService, GeneroService generoService, PlataformaService plataformaService) {
        this.jogoService = jogoService;
        this.devService = devService;
        this.generoService = generoService;
        this.plataformaService = plataformaService;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR JOGOS ---");
            System.out.println("1: Cadastrar novo jogo");
            System.out.println("2: Listar jogos");
            System.out.println("3: Atualizar jogo");
            System.out.println("4: Remover jogo");
            System.out.println("0: Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = ConsoleUtils.lerOpcao();

            try {
                switch (opcao) {
                    case 1: cadastrarJogo(); break;
                    case 2: listarJogos(); break;
                    case 3: atualizarJogo(); break;
                    case 4: removerJogo(); break;
                }
            } catch (Exception e) {
                System.err.println("\n[ERRO] " + e.getMessage());
            }
        }
    }

    private void cadastrarJogo() throws Exception {
        System.out.print("Título do jogo: ");
        String titulo = ConsoleUtils.lerString();
        System.out.print("Preço (ex: 199.90): ");
        double preco = ConsoleUtils.lerDouble();

        System.out.println("Selecione a desenvolvedora:");
        List<Desenvolvedora> devs = devService.listarTodos();
        if (devs.isEmpty()) {
            System.out.println("Nenhuma desenvolvedora cadastrada. Cadastre uma primeiro.");
            return;
        }
        devs.forEach(d -> System.out.printf("ID: %d | Nome: %s\n", d.getId(), d.getNome()));
        System.out.print("Digite o ID da Desenvolvedora: ");
        Long devId = ConsoleUtils.lerLong();
        Desenvolvedora dev = devService.buscarPorId(devId);
        if (dev == null) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.println("Selecione um ou mais gêneros:");
        List<Genero> generosDb = generoService.listarTodos();
        generosDb.forEach(g -> System.out.printf("ID: %d | Nome: %s\n", g.getId(), g.getNome()));
        Set<Genero> generosSelecionados = new HashSet<>();
        while (true) {
            System.out.print("Digite um ID de gênero (ou 0 para parar): ");
            Long genId = ConsoleUtils.lerLong();
            if (genId == 0) break;
            Genero g = generoService.buscarPorId(genId);
            if (g != null) {
                generosSelecionados.add(g);
                System.out.println("Gênero '" + g.getNome() + "' adicionado.");
            } else {
                System.out.println("ID de gênero inválido.");
            }
        }

        System.out.println("Selecione uma ou mais plataformas:");
        List<Plataforma> platsDb = plataformaService.listarTodos();
        platsDb.forEach(p -> System.out.printf("ID: %d | Nome: %s\n", p.getId(), p.getNome()));
        Set<Plataforma> plataformasSelecionadas = new HashSet<>();
         while (true) {
            System.out.print("Digite um ID de plataforma (ou 0 para parar): ");
            Long platId = ConsoleUtils.lerLong();
            if (platId == 0) break;
            Plataforma p = plataformaService.buscarPorId(platId);
            if (p != null) {
                plataformasSelecionadas.add(p);
                System.out.println("Plataforma '" + p.getNome() + "' adicionada.");
            } else {
                System.out.println("ID de plataforma inválido.");
            }
        }

        Jogo j = new Jogo();
        j.setTitulo(titulo);
        j.setPreco(preco);
        j.setDesenvolvedora(dev);
        j.setGeneros(generosSelecionados);
        j.setPlataformas(plataformasSelecionadas);
        
        jogoService.salvar(j);
        System.out.println("Jogo '" + j.getTitulo() + "' salvo com sucesso! ID: " + j.getId());
    }

    public void listarJogos() {
        List<Jogo> jogos = jogoService.listarTodos();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de jogos ---");
        for (Jogo j : jogos) {
            System.out.printf("ID: %d | Título: %s | Preço: R$%.2f | Dev: %s\n", 
                j.getId(), j.getTitulo(), j.getPreco(), j.getDesenvolvedora().getNome());
        }
    }
    
    private void atualizarJogo() throws Exception {
        System.out.print("Digite o ID do jogo para atualizar: ");
        Long id = ConsoleUtils.lerLong();
        Jogo j = jogoService.buscarPorId(id);
        if (j == null) {
            System.out.println("Jogo não encontrado.");
            return;
        }
        
        System.out.print("Novo título (atual: '" + j.getTitulo() + "' - deixe em branco para manter): ");
        String titulo = ConsoleUtils.lerString();
        if (!titulo.trim().isEmpty()) {
            j.setTitulo(titulo);
        }

        System.out.print("Novo preço (atual: " + j.getPreco() + " - digite -1 para manter): ");
        double preco = ConsoleUtils.lerDouble();
        if (preco >= 0) {
            j.setPreco(preco);
        }
        
        jogoService.salvar(j);
        System.out.println("Jogo atualizado com sucesso!");
    }

    private void removerJogo() throws Exception {
        System.out.print("Digite o ID do jogo para remover: ");
        Long id = ConsoleUtils.lerLong();
        jogoService.remover(id);
        System.out.println("Jogo removido com sucesso!");
    }
}
