package br.com.biblioteca.negocio;


import br.com.biblioteca.dao.PlataformaDAO;
import br.com.biblioteca.entidades.Plataforma;
import java.util.List;

public class PlataformaService {

    private PlataformaDAO plataformaDAO;

    public PlataformaService() {
        this.plataformaDAO = new PlataformaDAO();
    }

    public void salvar(Plataforma plataforma) {
        if (plataforma.getNome() == null || plataforma.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da plataforma é obrigatório.");
        }
        
        if (plataforma.getId() != null) {
            plataformaDAO.atualizar(plataforma);
        } else {
            plataformaDAO.salvar(plataforma);
        }
    }

    public Plataforma buscarPorId(Long id) {
        return plataformaDAO.buscarPorId(id);
    }

    public List<Plataforma> listarTodos() {
        return plataformaDAO.listarTodos();
    }

    public void remover(Long id) {
        plataformaDAO.remover(id);
    }
}
