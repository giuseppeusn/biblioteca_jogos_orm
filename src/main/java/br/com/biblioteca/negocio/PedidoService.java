package br.com.biblioteca.negocio;


import br.com.biblioteca.dao.PedidoDAO;
import br.com.biblioteca.entidades.Pedido;
import java.util.List;

public class PedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
    }

    public void salvar(Pedido pedido) {
        if (pedido.getUsuario() == null) {
            throw new IllegalArgumentException("O pedido deve estar associado a um usuário.");
        }
        if (pedido.getDataPedido() == null) {
            throw new IllegalArgumentException("A data do pedido é obrigatória.");
        }
        
        if (pedido.getId() != null) {
            pedidoDAO.atualizar(pedido);
        } else {
            pedidoDAO.salvar(pedido);
        }
    }

    public Pedido buscarPorId(Long id) {
        return pedidoDAO.buscarPorId(id);
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }

    public void remover(Long id) {
        pedidoDAO.remover(id);
    }
}
