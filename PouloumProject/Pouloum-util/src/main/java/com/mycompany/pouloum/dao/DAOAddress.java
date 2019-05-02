package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Address;

public class DAOAddress {

    public static void persist(Address i)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.persist(i);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            Address a = (Address) em.find(Address.class,id);
            em.remove(a);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Address findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Address found = null;
        try {
            found = em.find(Address.class, id);
        } catch (Exception e) {
            throw e;
        }

        return found;
    }

    public static List<Address> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        List<Address> found = null;
        try {
            Query q = em.createQuery("SELECT e FROM Address e");
            found = (List<Address>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return found;
    }

}
