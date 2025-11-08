package br.com.biblioteca;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Usuario;

public class Main {
    public static void main(String[] args) {
        Usuario usuario1 = new Usuario("Joao", "joao@gmail.com");
        Usuario usuario2 = new Usuario("Pedro", "pedro@gmail.com");

        UsuarioDAO dao = new UsuarioDAO();
        dao.salvar(usuario1);
        dao.salvar(usuario2);
    }
}