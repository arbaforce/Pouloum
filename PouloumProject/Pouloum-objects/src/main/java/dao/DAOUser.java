package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.User;


public class DAOUser {
    
    public static void persist( User u )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            em.persist(u);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            User u = (User) em.find(User.class,id);
            em.remove(u);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static User updateUser( User u )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try{
            em.merge(u);
        } catch (Exception e) {
            throw e;
        }
        
        return u;
    }
    
    public static User findById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        User found = null;
        try {
            found = em.find(User.class, id);
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
    public static User findUserByEmail( String email )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        User found = null;
        try {
            Query q = em.createQuery("SELECT u FROM User u where u.email=:email");
            q.setParameter("email", email);
            try {
                found = (User) q.getSingleResult();
            } catch (NoResultException nr) {
                // throw nr;
            }
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
    public static User findUserByNickname( String nickname )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        User found = null;
        try {
            Query q = em.createQuery("SELECT u FROM User u where u.nickname=:nickname");
            q.setParameter("nickname", nickname);
            try {
                found = (User) q.getSingleResult();
            } catch (NoResultException nr) {
                // throw nr;
            }
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
    public static User findUserByEmailAndPassword( String email, String password )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        User found = null;
        
        try {
            Query q = em.createQuery("SELECT u FROM User u where u.email=:email and u.password=:password");
            q.setParameter("email",email);
            q.setParameter("password",password);
            try {
                found =(User) q.getSingleResult();
            } catch (NoResultException nr) {
                throw nr;
            }
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
    public static List<User> findAll( )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        List<User> found = null;
        try {
            Query q = em.createQuery("SELECT u FROM User u");
            found = (List<User>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        
        return found;
    }
    
}
