package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Jogo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class JogoDAO {

    private EntityManagerFactory emf;

    public JogoDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(Jogo jogo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(jogo);
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

    public Jogo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Jogo.class, id);
        } finally {
            em.close();
        }
    }

    public List<Jogo> buscarPorDesenvolvedoraId(Long desenvolvedoraId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT j FROM Jogo j WHERE j.desenvolvedora.id = :id", Jogo.class)
                    .setParameter("id", desenvolvedoraId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Jogo> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Jogo j", Jogo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Jogo jogo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(jogo);
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
            Jogo jogo = em.find(Jogo.class, id);
            if (jogo != null) {
                em.remove(jogo);
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
