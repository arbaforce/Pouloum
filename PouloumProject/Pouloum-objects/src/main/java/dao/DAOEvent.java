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
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        try {
            em.persist(i);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em=JpaUtil.obtenirEntityManager();
        
        try {
            Event i = (Event) em.find(Event.class,id);
            em.remove(i);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static Event updateEvent( Event i )
        throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        try{
            em.merge(i);
        }catch(Exception e){           
            throw e;         
        }
        
        return i;
    }
    
    public static Event findById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Event found = null;
        try {
            found = em.find(Event.class, id);
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
    public static List<Event> findAll( )
        throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();        
        List<Event> found = null;
        try {
            Query q = em.createQuery("SELECT u FROM User u");
            found = (List<Event>) q.getResultList();            
        } catch (Exception e) {
            throw e;         
        }
        
        return found;   
    }
    
}
