package br.com.biblioteca.apresentacao;


import br.com.biblioteca.entidades.Usuario;
import br.com.biblioteca.negocio.UsuarioService;
import java.util.List;

public class UsuarioView {

    private final UsuarioService usuarioService;

    public UsuarioView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR USUÁRIOS ---");
            System.out.println("1: Cadastrar novo usuário");
            System.out.println("2: Listar usuários");
            System.out.println("3: Atualizar usuário");
            System.out.println("4: Remover usuário");
            System.out.println("0: Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = ConsoleUtils.lerOpcao();

            try {
                switch (opcao) {
                    case 1: cadastrarUsuario(); break;
                    case 2: listarUsuarios(); break;
                    case 3: atualizarUsuario(); break;
                    case 4: removerUsuario(); break;
                }
            } catch (Exception e) {
                System.err.println("\n[ERRO] " + e.getMessage());
            }
        }
    }

    private void cadastrarUsuario() throws Exception {
        System.out.print("Nome: ");
        String nome = ConsoleUtils.lerString();
        System.out.print("Email: ");
        String email = ConsoleUtils.lerString();

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        usuarioService.salvar(u);
        System.out.println("Usuário cadastrado com sucesso! ID: " + u.getId());
    }

    public void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de usuários ---");
        for (Usuario u : usuarios) {
            System.out.printf("ID: %d | Nome: %s | Email: %s\n", u.getId(), u.getNome(), u.getEmail());
        }
    }

    private void atualizarUsuario() throws Exception {
        System.out.print("Digite o ID do usuário para atualizar: ");
        Long id = ConsoleUtils.lerLong();
        Usuario u = usuarioService.buscarPorId(id);
        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Novo nome (atual: '" + u.getNome() + "' - deixe em branco para manter): ");
        String nome = ConsoleUtils.lerString();
        if (!nome.trim().isEmpty()) {
            u.setNome(nome);
        }

        System.out.print("Novo email (atual: '" + u.getEmail() + "' - deixe em branco para manter): ");
        String email = ConsoleUtils.lerString();
        if (!email.trim().isEmpty()) {
            u.setEmail(email);
        }

        usuarioService.salvar(u);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void removerUsuario() throws Exception {
        System.out.print("Digite o ID do usuário para remover: ");
        Long id = ConsoleUtils.lerLong();
        
        Usuario u = usuarioService.buscarPorId(id);
        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        System.out.print("Tem certeza que deseja remover '" + u.getNome() + "'? (s/n): ");
        String confirm = ConsoleUtils.lerString();

        if (confirm.equalsIgnoreCase("s")) {
            usuarioService.remover(id);
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }
}