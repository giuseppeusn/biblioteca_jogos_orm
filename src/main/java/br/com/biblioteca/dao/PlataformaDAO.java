package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Plataforma;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PlataformaDAO {

    private EntityManagerFactory emf;

    public PlataformaDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(Plataforma plataforma) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(plataforma);
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

    public Plataforma buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Plataforma.class, id);
        } finally {
            em.close();
        }
    }

    public List<Plataforma> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Plataforma p", Plataforma.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Plataforma plataforma) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(plataforma);
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
            Plataforma plataforma = em.find(Plataforma.class, id);
            if (plataforma != null) {
                em.remove(plataforma);
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