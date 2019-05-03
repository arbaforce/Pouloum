package com.mycompany.pouloum.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Event;

public class DAOEvent {

    public static void persist(Event e)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.persist(e);
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Event e = (Event) em.find(Event.class, id);
        em.remove(e);
    }

    public static Event updateEvent(Event e)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        e = em.merge(e);
        return e;
    }

    public static Event findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Event found = (Event) em.find(Event.class, id);
        return found;
    }

    public static List<Event> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT e FROM Event e");
        List<Event> found = (List<Event>) q.getResultList();
        return found;
    }

}
