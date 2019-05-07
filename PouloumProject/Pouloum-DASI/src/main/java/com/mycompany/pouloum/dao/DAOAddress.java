package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Address;

public class DAOAddress {

    public static void persist(Address a)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.persist(a);
    }
    
    public static void removeById( Long id )
        throws Exception
    {
        EntityManager em = JpaUtil.getEntityManager();
        
        Address a = (Address) em.find(Address.class,id);
        em.remove(a);
    }

    public static Address findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Address found = (Address) em.find(Address.class, id);
        return found;
    }

    public static List<Address> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT a FROM Address a");
        List<Address> found = (List<Address>) q.getResultList();
        return found;
    }

    public static List<Long> findAllIDs()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT a.id FROM Address a");
        List<Long> found = (List<Long>) q.getResultList();
        return found;
    }

}
