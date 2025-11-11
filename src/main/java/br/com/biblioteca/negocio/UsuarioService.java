package br.com.biblioteca.negocio;

import br.com.biblioteca.dao.PedidoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Pedido;
import br.com.biblioteca.entidades.Usuario;
import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;
    private PedidoDAO pedidoDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.pedidoDAO = new PedidoDAO();
    }

    public void salvar(Usuario usuario) throws Exception {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new Exception("O nome do usuário não pode ser nulo ou vazio.");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
             throw new Exception("E-mail inválido.");
        }

        if (usuario.getId() == null) {
            Usuario existente = usuarioDAO.buscarPorEmail(usuario.getEmail());
            if (existente != null) {
                throw new Exception("Este e-mail já está cadastrado.");
            }
        }
        
        if (usuario.getId() != null) {
            usuarioDAO.atualizar(usuario);
        } else {
            usuarioDAO.salvar(usuario);
        }
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public void remover(Long id) throws Exception {
        List<Pedido> pedidos = pedidoDAO.buscarPorUsuarioId(id);
        
        if (!pedidos.isEmpty()) {
            throw new Exception("Não é possível remover este usuário, pois ele possui " + pedidos.size() + " pedido(s) associado(s).");
      
        }

        usuarioDAO.remover(id);
    }
}