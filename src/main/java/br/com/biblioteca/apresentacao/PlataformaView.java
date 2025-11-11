package br.com.biblioteca.apresentacao;

import br.com.biblioteca.entidades.Plataforma;
import br.com.biblioteca.negocio.PlataformaService;
import java.util.List;

public class PlataformaView {

    private final PlataformaService plataformaService;

    public PlataformaView(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR PLATAFORMAS ---");
            System.out.println("1: Cadastrar nova plataforma");
            System.out.println("2: Listar plataformas");
            System.out.println("3: Atualizar plataforma");
            System.out.println("4: Remover plataforma");
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
        System.out.print("Nome da plataforma: ");
        String nome = ConsoleUtils.lerString();
        Plataforma p = new Plataforma();
        p.setNome(nome);
        plataformaService.salvar(p);
        System.out.println("Plataforma cadastrada! ID: " + p.getId());
    }

    public void listar() {
        List<Plataforma> plataformas = plataformaService.listarTodos();
        if (plataformas.isEmpty()) {
            System.out.println("Nenhuma plataforma cadastrada.");
            return;
        }
        System.out.println("\n--- Lista de plataformas ---");
        for (Plataforma p : plataformas) {
            System.out.printf("ID: %d | Nome: %s\n", p.getId(), p.getNome());
        }
    }

    private void atualizar() throws Exception {
        System.out.print("ID da plataforma para atualizar: ");
        Long idAtt = ConsoleUtils.lerLong();
        Plataforma pAtt = plataformaService.buscarPorId(idAtt);
        if (pAtt == null) {
            System.out.println("ID não encontrado.");
            return;
        }
        System.out.print("Novo nome (atual: '" + pAtt.getNome() + "'): ");
        String novoNome = ConsoleUtils.lerString();
         if (!novoNome.trim().isEmpty()) {
            pAtt.setNome(novoNome);
            plataformaService.salvar(pAtt);
            System.out.println("Atualizado com sucesso!");
        } else {
            System.out.println("Nenhuma alteração feita.");
        }
    }

    private void remover() throws Exception {
        System.out.print("ID da plataforma para remover: ");
        Long idRem = ConsoleUtils.lerLong();
        plataformaService.remover(idRem);
        System.out.println("Removido com sucesso!");
    }
}
