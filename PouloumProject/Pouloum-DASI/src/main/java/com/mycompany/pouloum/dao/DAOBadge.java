package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Badge;

public class DAOBadge {

    public static void persist(Badge b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.persist(b);
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Badge b = (Badge) em.find(Badge.class, id);
        em.remove(b);
    }

    public static Badge updateBadge(Badge b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        b = em.merge(b);
        return b;
    }

    public static Badge findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Badge found = (Badge) em.find(Badge.class, id);
        return found;
    }

    public static List<Badge> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT e FROM Event e");
        List<Badge> found = (List<Badge>) q.getResultList();
        return found;
    }

}
