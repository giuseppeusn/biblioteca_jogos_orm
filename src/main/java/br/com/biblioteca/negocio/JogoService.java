package br.com.biblioteca.negocio;

import br.com.biblioteca.dao.ItemPedidoDAO;
import br.com.biblioteca.dao.JogoDAO;
import br.com.biblioteca.entidades.ItemPedido;
import br.com.biblioteca.entidades.Jogo;
import java.util.List;

public class JogoService {

    private JogoDAO jogoDAO;
    private ItemPedidoDAO itemPedidoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
        this.itemPedidoDAO = new ItemPedidoDAO();
    }

    public void salvar(Jogo jogo) throws Exception {
        if (jogo.getTitulo() == null || jogo.getTitulo().trim().isEmpty()) {
            throw new Exception("O título do jogo é obrigatório.");
        }
        if (jogo.getPreco() < 0) {
            throw new Exception("O preço do jogo não pode ser negativo.");
        }
        if (jogo.getDesenvolvedora() == null) {
            throw new Exception("O jogo deve ter uma desenvolvedora.");
        }
        
        if (jogo.getId() != null) {
            jogoDAO.atualizar(jogo);
        } else {
            jogoDAO.salvar(jogo);
        }
    }

    public Jogo buscarPorId(Long id) {
        return jogoDAO.buscarPorId(id);
    }

    public List<Jogo> listarTodos() {
        return jogoDAO.listarTodos();
    }

    public void remover(Long id) throws Exception {
        List<ItemPedido> itens = itemPedidoDAO.buscarPorJogoId(id);

        if (!itens.isEmpty()) {
            throw new Exception("Não é possível remover este jogo, pois ele está associado a " + itens.size() + " pedido(s).");
    
        }
        
        jogoDAO.remover(id);
    }
}
