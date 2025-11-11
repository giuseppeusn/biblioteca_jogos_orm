package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.ItemPedido;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ItemPedidoDAO {

    private EntityManagerFactory emf;

    public ItemPedidoDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public void salvar(ItemPedido itemPedido) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(itemPedido);
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

    public ItemPedido buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ItemPedido.class, id);
        } finally {
            em.close();
        }
    }

    public List<ItemPedido> buscarPorJogoId(Long jogoId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM ItemPedido i WHERE i.jogo.id = :id", ItemPedido.class)
                    .setParameter("id", jogoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ItemPedido> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM ItemPedido i", ItemPedido.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(ItemPedido itemPedido) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(itemPedido);
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
            ItemPedido itemPedido = em.find(ItemPedido.class, id);
            if (itemPedido != null) {
                em.remove(itemPedido);
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
