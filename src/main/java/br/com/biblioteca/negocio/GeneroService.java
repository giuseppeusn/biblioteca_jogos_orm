package br.com.biblioteca.negocio;

import br.com.biblioteca.dao.GeneroDAO;
import br.com.biblioteca.entidades.Genero;
import java.util.List;

public class GeneroService {

    private GeneroDAO generoDAO;

    public GeneroService() {
        this.generoDAO = new GeneroDAO();
    }

    public void salvar(Genero genero) {
        if (genero.getNome() == null || genero.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do gênero é obrigatório.");
        }
        
        if (genero.getId() != null) {
            generoDAO.atualizar(genero);
        } else {
            generoDAO.salvar(genero);
        }
    }

    public Genero buscarPorId(Long id) {
        return generoDAO.buscarPorId(id);
    }

    public List<Genero> listarTodos() {
        return generoDAO.listarTodos();
    }

    public void remover(Long id) {
        generoDAO.remover(id);
    }
}
