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

        em.persist(a);
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Activity a = (Activity) em.find(Activity.class, id);
        em.remove(a);
    }

    public static Activity updateActivity(Activity a)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.merge(a);
        return a;
    }

    public static Activity findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Activity found = (Activity) em.find(Activity.class, id);
        return found;
    }

    public static List<Activity> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Query q = em.createQuery("SELECT a FROM Activity a");
        List<Activity> found = (List<Activity>) q.getResultList();
        return found;
    }

}
