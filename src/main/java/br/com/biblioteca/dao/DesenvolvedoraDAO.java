package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Desenvolvedora;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DesenvolvedoraDAO {

    private EntityManagerFactory emf;

    public DesenvolvedoraDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(Desenvolvedora desenvolvedora) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(desenvolvedora);
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

    public Desenvolvedora buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Desenvolvedora.class, id);
        } finally {
            em.close();
        }
    }

    public List<Desenvolvedora> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Desenvolvedora d", Desenvolvedora.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Desenvolvedora desenvolvedora) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(desenvolvedora);
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
            Desenvolvedora desenvolvedora = em.find(Desenvolvedora.class, id);
            if (desenvolvedora != null) {
                em.remove(desenvolvedora);
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
