package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.BadgeEvolved;

public class DAOBadge {

    public static void persist(BadgeEvolved b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.persist(b);
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        BadgeEvolved b = (BadgeEvolved) em.find(BadgeEvolved.class, id);
        em.remove(b);
    }

    public static BadgeEvolved updateBadge(BadgeEvolved b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        b = em.merge(b);
        return b;
    }

    public static BadgeEvolved findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        BadgeEvolved found = (BadgeEvolved) em.find(BadgeEvolved.class, id);
        return found;
    }

    public static List<BadgeEvolved> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT b FROM BadgeEvolved b");
        List<BadgeEvolved> found = (List<BadgeEvolved>) q.getResultList();
        return found;
    }

    public static List<Long> findAllIDs()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT b.id FROM BadgeEvolved b");
        List<Long> found = (List<Long>) q.getResultList();
        return found;
    }

}
