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

        try {
            em.persist(e);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            Event e = (Event) em.find(Event.class, id);
            em.remove(e);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Event updateEvent(Event e)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            e = em.merge(e);
            return e;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Event findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Event found = null;
        
        try {
            found = (Event) em.find(Event.class, id);
            return found;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static List<Event> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            Query q = em.createQuery("SELECT e FROM Event e");
            List<Event> found = (List<Event>) q.getResultList();
            return found;
        } catch (Exception ex) {
            throw ex;
        }
    }

}
