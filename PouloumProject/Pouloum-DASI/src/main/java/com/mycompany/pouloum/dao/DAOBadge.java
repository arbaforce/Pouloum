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
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            Badge b = (Badge) em.find(Badge.class, id);
            em.remove(b);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Badge updateBadge(Badge b)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            b = em.merge(b);
        } catch (Exception ex) {
            throw ex;
        }

        return b;
    }

    public static Badge findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Badge found = null;
        try {
            found = (Badge) em.find(Badge.class, id);
        } catch (Exception ex) {
            throw ex;
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
        } catch (Exception ex) {
            throw ex;
        }

        return found;
    }

}
