package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Event;


public class DAOEvent {
    
    public static void persist( Event i )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            em.persist(i);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em=JpaUtil.getEntityManager();
        
        try {
            Event i = (Event) em.find(Event.class,id);
            em.remove(i);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static Event updateEvent( Event i )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            em.merge(i);
        } catch (Exception ex) {
            throw ex;
        }
        
        return i;
    }
    
    public static Event findById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Event found = null;
        try {
            found = em.find(Event.class, id);
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
    public static List<Event> findAll( )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        List<Event> found = null;
        try {
            Query q = em.createQuery("SELECT e FROM Event e");
            found = (List<Event>) q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
}