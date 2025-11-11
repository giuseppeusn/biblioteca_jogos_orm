package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Genero;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GeneroDAO {

    private EntityManagerFactory emf;

    public GeneroDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(Genero genero) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(genero);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Genero buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public List<Genero> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Genero g", Genero.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Genero genero) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(genero);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Genero genero = em.find(Genero.class, id);
            if (genero != null) {
                em.remove(genero);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
