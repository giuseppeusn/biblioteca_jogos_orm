package br.com.biblioteca.negocio;

import br.com.biblioteca.dao.ItemPedidoDAO;
import br.com.biblioteca.entidades.ItemPedido;
import java.util.List;

public class ItemPedidoService {

    private ItemPedidoDAO itemPedidoDAO;

    public ItemPedidoService() {
        this.itemPedidoDAO = new ItemPedidoDAO();
    }

    public void salvar(ItemPedido itemPedido) {
        if (itemPedido.getJogo() == null) {
            throw new IllegalArgumentException("O item deve ter um jogo associado.");
        }
        if (itemPedido.getPedido() == null) {
            throw new IllegalArgumentException("O item deve pertencer a um pedido.");
        }
        if (itemPedido.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser ao menos 1.");
        }
        
        if (itemPedido.getId() != null) {
            itemPedidoDAO.atualizar(itemPedido);
        } else {
            itemPedidoDAO.salvar(itemPedido);
        }
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoDAO.buscarPorId(id);
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoDAO.listarTodos();
    }

    public void remover(Long id) {
        itemPedidoDAO.remover(id);
    }
}
