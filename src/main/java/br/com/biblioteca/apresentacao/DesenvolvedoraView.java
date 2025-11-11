package br.com.biblioteca.apresentacao;

import br.com.biblioteca.entidades.Desenvolvedora;
import br.com.biblioteca.negocio.DesenvolvedoraService;
import java.util.List;

public class DesenvolvedoraView {

    private final DesenvolvedoraService devService;

    public DesenvolvedoraView(DesenvolvedoraService devService) {
        this.devService = devService;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR DESENVOLVEDORAS ---");
            System.out.println("1: Cadastrar nova desenvolvedora");
            System.out.println("2: Listar desenvolvedoras");
            System.out.println("3: Atualizar desenvolvedora");
            System.out.println("4: Remover desenvolvedora");
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
        System.out.print("Nome da desenvolvedora: ");
        String nome = ConsoleUtils.lerString();
        Desenvolvedora d = new Desenvolvedora();
        d.setNome(nome);
        devService.salvar(d);
        System.out.println("Desenvolvedora cadastrada! ID: " + d.getId());
    }

    public void listar() {
        List<Desenvolvedora> devs = devService.listarTodos();
        if (devs.isEmpty()) {
            System.out.println("Nenhuma desenvolvedora cadastrada.");
            return;
        }
        System.out.println("\n--- Lista de desenvolvedoras ---");
        for (Desenvolvedora dev : devs) {
            System.out.printf("ID: %d | Nome: %s\n", dev.getId(), dev.getNome());
        }
    }

    private void atualizar() throws Exception {
        System.out.print("ID da Desenvolvedora para atualizar: ");
        Long idAtt = ConsoleUtils.lerLong();
        Desenvolvedora devAtt = devService.buscarPorId(idAtt);
        if (devAtt == null) {
            System.out.println("ID não encontrado.");
            return;
        }
        System.out.print("Novo nome (atual: '" + devAtt.getNome() + "'): ");
        String novoNome = ConsoleUtils.lerString();
        if (!novoNome.trim().isEmpty()) {
            devAtt.setNome(novoNome);
            devService.salvar(devAtt);
            System.out.println("Atualizado com sucesso!");
        } else {
            System.out.println("Nenhuma alteração feita.");
        }
    }

    private void remover() throws Exception {
        System.out.print("ID da desenvolvedora para remover: ");
        Long idRem = ConsoleUtils.lerLong();
        devService.remover(idRem);
        System.out.println("Removido com sucesso!");
    }
}
