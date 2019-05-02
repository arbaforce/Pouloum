package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Pouloumer;


public class DAOPouloumer {
    
    public static void persist( Pouloumer u )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            em.persist(u);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            Pouloumer u = (Pouloumer) em.find(Pouloumer.class,id);
            em.remove(u);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static Pouloumer updateUser( Pouloumer u )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try{
            em.merge(u);
        } catch (Exception ex) {
            throw ex;
        }
        
        return u;
    }
    
    public static Pouloumer findById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Pouloumer found = null;
        try {
            found = em.find(Pouloumer.class, id);
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
    public static Pouloumer findUserByEmail( String email )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Pouloumer found = null;
        try {
            Query q = em.createQuery("SELECT u FROM Pouloumer u where u.email=:email");
            q.setParameter("email", email);
            try {
                found = (Pouloumer) q.getSingleResult();
            } catch (NoResultException nr) {
                // throw nr;
            }
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
    public static Pouloumer findUserByNickname( String nickname )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Pouloumer found = null;
        try {
            Query q = em.createQuery("SELECT u FROM Pouloumer u where u.nickname=:nickname");
            q.setParameter("nickname", nickname);
            try {
                found = (Pouloumer) q.getSingleResult();
            } catch (NoResultException nr) {
                // throw nr;
            }
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
    public static Pouloumer findUserByEmailAndPassword( String email, String password )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Pouloumer found = null;
        
        try {
            Query q = em.createQuery("SELECT u FROM Pouloumer u where u.email=:email and u.password=:password");
            q.setParameter("email",email);
            q.setParameter("password",password);
            try {
                found =(Pouloumer) q.getSingleResult();
            } catch (NoResultException nr) {
                throw nr;
            }
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
    public static List<Pouloumer> findAll( )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        List<Pouloumer> found = null;
        try {
            Query q = em.createQuery("SELECT u FROM Pouloumer u");
            found = (List<Pouloumer>) q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        
        return found;
    }
    
}