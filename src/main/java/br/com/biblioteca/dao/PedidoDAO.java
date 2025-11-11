package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Pedido;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PedidoDAO {

    private EntityManagerFactory emf;

    public PedidoDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(Pedido pedido) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(pedido);
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

    public Pedido buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPorUsuarioId(Long usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedido p WHERE p.usuario.id = :id", Pedido.class)
                    .setParameter("id", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pedido> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Pedido p", Pedido.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Pedido pedido) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(pedido);
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
            Pedido pedido = em.find(Pedido.class, id);
            if (pedido != null) {
                em.remove(pedido);
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
