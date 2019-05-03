package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Address;

public class DAOAddress {

    public static void persist(Address a)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.persist(a);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            Address a = (Address) em.find(Address.class,id);
            em.remove(a);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Address findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Address found = null;
        try {
            found = (Address) em.find(Address.class, id);
        } catch (Exception ex) {
            throw ex;
        }

        return found;
    }

    public static List<Address> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        List<Address> found = null;
        try {
            Query q = em.createQuery("SELECT a FROM Address a");
            found = (List<Address>) q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }

        return found;
    }

}
