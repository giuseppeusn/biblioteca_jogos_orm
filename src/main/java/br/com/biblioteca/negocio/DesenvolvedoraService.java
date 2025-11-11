package br.com.biblioteca.negocio;


import br.com.biblioteca.dao.DesenvolvedoraDAO;
import br.com.biblioteca.dao.JogoDAO;
import br.com.biblioteca.entidades.Desenvolvedora;
import br.com.biblioteca.entidades.Jogo;

import java.util.List;

public class DesenvolvedoraService {

    private DesenvolvedoraDAO desenvolvedoraDAO;
    private JogoDAO jogoDAO;

    public DesenvolvedoraService() {
        this.desenvolvedoraDAO = new DesenvolvedoraDAO();
        this.jogoDAO = new JogoDAO();
    }

    public void salvar(Desenvolvedora desenvolvedora) throws Exception {
        if (desenvolvedora.getNome() == null || desenvolvedora.getNome().trim().isEmpty()) {
            throw new Exception("O nome da desenvolvedora é obrigatório.");
        }
        
        if (desenvolvedora.getId() != null) {
            desenvolvedoraDAO.atualizar(desenvolvedora);
        } else {
            desenvolvedoraDAO.salvar(desenvolvedora);
        }
    }

    public Desenvolvedora buscarPorId(Long id) {
        return desenvolvedoraDAO.buscarPorId(id);
    }

    public List<Desenvolvedora> listarTodos() {
        return desenvolvedoraDAO.listarTodos();
    }

    public void remover(Long id) throws Exception {
        List<Jogo> jogos = jogoDAO.buscarPorDesenvolvedoraId(id);

        if (!jogos.isEmpty()) {
            throw new Exception("Não é possível remover esta desenvolvedora, pois ela possui " + jogos.size() + " jogo(s) associado(s).");
      
        }
        desenvolvedoraDAO.remover(id);
    }
}
