package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Activity;

public class DAOActivity {

    public static void persist(Activity a)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.persist(a);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            Activity a = (Activity) em.find(Activity.class, id);
            em.remove(a);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Activity updateUser(Activity a)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.merge(a);
        } catch (Exception ex) {
            throw ex;
        }

        return a;
    }

    public static Activity findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Activity found = null;

        try {
            found = em.find(Activity.class, id);
        } catch (Exception ex) {
            throw ex;
        }

        return found;
    }

    public static List<Activity> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        List<Activity> found = null;

        try {
            Query q = em.createQuery("SELECT a FROM Activity a");
            found = (List<Activity>) q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }

        return found;
    }

}
