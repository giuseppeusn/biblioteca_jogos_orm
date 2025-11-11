package br.com.biblioteca.apresentacao;

import br.com.biblioteca.entidades.Genero;
import br.com.biblioteca.negocio.GeneroService;
import java.util.List;

public class GeneroView {

    private final GeneroService generoService;

    public GeneroView(GeneroService generoService) {
        this.generoService = generoService;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR GÊNEROS ---");
            System.out.println("1: Cadastrar novo gênero");
            System.out.println("2: Listar gêneros");
            System.out.println("3: Atualizar gênero");
            System.out.println("4: Remover gênero");
            System.out.println("0: Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = ConsoleUtils.lerOpcao();

            try {
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3: atualizar(); break;
                    case 4: remover(); break;
                }
            } catch (Exception e) {
                System.err.println("\n[ERRO] " + e.getMessage());
            }
        }
    }

    private void cadastrar() throws Exception {
        System.out.print("Nome do gênero: ");
        String nome = ConsoleUtils.lerString();
        Genero g = new Genero();
        g.setNome(nome);
        generoService.salvar(g);
        System.out.println("Gênero cadastrado! ID: " + g.getId());
    }

    public void listar() {
        List<Genero> generos = generoService.listarTodos();
        if (generos.isEmpty()) {
            System.out.println("Nenhum gênero cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de gêneros ---");
        for (Genero g : generos) {
            System.out.printf("ID: %d | Nome: %s\n", g.getId(), g.getNome());
        }
    }

    private void atualizar() throws Exception {
        System.out.print("ID do gênero para atualizar: ");
        Long idAtt = ConsoleUtils.lerLong();
        Genero gAtt = generoService.buscarPorId(idAtt);
        if (gAtt == null) {
            System.out.println("ID não encontrado.");
            return;
        }
        System.out.print("Novo nome (atual: '" + gAtt.getNome() + "'): ");
        String novoNome = ConsoleUtils.lerString();
        if (!novoNome.trim().isEmpty()) {
            gAtt.setNome(novoNome);
            generoService.salvar(gAtt);
            System.out.println("Atualizado com sucesso!");
        } else {
            System.out.println("Nenhuma alteração feita.");
        }
    }

    private void remover() throws Exception {
        System.out.print("ID do gênero para remover: ");
        Long idRem = ConsoleUtils.lerLong();
        generoService.remover(idRem);
        System.out.println("Removido com sucesso!");
    }
}
