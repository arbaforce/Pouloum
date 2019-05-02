package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Badge;

public class DAOBadge {

    public static void persist(Badge b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.persist(b);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Badge findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Badge found = null;
        try {
            found = em.find(Badge.class, id);
        } catch (Exception e) {
            throw e;
        }

        return found;
    }

    public static List<Badge> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        List<Badge> found = null;
        try {
            Query q = em.createQuery("SELECT e FROM Event e");
            found = (List<Badge>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return found;
    }

}
