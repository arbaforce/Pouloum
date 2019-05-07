package com.mycompany.pouloum.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.mycompany.pouloum.model.Comment;

public class DAOComment {

    public static void persist(Comment c)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        em.persist(c);
    }

    public static void removeById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Comment c = (Comment) em.find(Comment.class, id);
        em.remove(c);
    }

    public static Comment updateBadge(Comment c)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        c = em.merge(c);
        return c;
    }

    public static Comment findById(Long id)
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();

        Comment found = (Comment) em.find(Comment.class, id);
        return found;
    }

    public static List<Comment> findAll()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT c FROM Comment c");
        List<Comment> found = (List<Comment>) q.getResultList();
        return found;
    }

    public static List<Long> findAllIDs()
            throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        
        Query q = em.createQuery("SELECT c.id FROM Comment c");
        List<Long> found = (List<Long>) q.getResultList();
        return found;
    }

}
