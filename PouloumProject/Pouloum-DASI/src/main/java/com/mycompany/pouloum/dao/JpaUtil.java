package com.mycompany.pouloum.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 * Cette classe fournit des méthodes statiques utiles pour accéder aux
 * fonctionnalités de JPA (Entity Manager, Entity Transaction). Le nom de
 * l'unité de persistance (PERSISTENCE_UNIT_NAME) doit être conforme à la
 * configuration indiquée dans le fichier persistence.xml du projet.
 *
 * @author DASI Team
 */
public class JpaUtil {
    
    /**
     * Nom de l'unité de persistance utilisée par la Factory de Entity Manager.
     * <br><strong>Vérifier le nom de l'unité de persistance
     * (cf.&nbsp;persistence.xml)</strong>
     */
    public static final String PERSISTENCE_UNIT_NAME = "Pouloum-objects";
    
    /**
     * Factory de Entity Manager liée à l'unité de persistance.
     * <br/><strong>Vérifier le nom de l'unité de persistance indiquée dans
     * l'attribut statique PERSISTENCE_UNIT_NAME
     * (cf.&nbsp;persistence.xml)</strong>
     */
    private static EntityManagerFactory entityManagerFactory = null;
    
    /**
     * Gère les instances courantes de Entity Manager liées aux Threads.
     * L'utilisation de ThreadLocal garantie une unique instance courante par
     * Thread.
     */
    private static final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<EntityManager>() {
        @Override
        protected EntityManager initialValue() {
            return null;
        }
    };

    // Méthode pour avoir des messages de Log dans le bon ordre (pause)
    private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // e.hashCode();
        }
    }

    // Méthode pour avoir des messages de Log dans le bon ordre (log)
    private static void log(String message) {
        System.out.flush();
        pause(5);
        System.err.println("[JpaUtil:Log] " + message);
        System.err.flush();
        pause(5);
    }

    /**
     * Initialise la Factory de Entity Manager.
     * <br><strong>À utiliser uniquement au début de la méthode main() [projet
     * Java Application] ou dans la méthode init() de la Servlet Contrôleur
     * (ActionServlet) [projet Web Application].</strong>
     */
    public static synchronized void init() {
        log("Initializing the entity manager factory");
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /**
     * Libère la Factory de Entity Manager.
     * <br><strong>À utiliser uniquement à la fin de la méthode main() [projet
     * Java Application] ou dans la méthode destroy() de la Servlet Contrôleur
     * (ActionServlet) [projet Web Application].</strong>
     */
    public static synchronized void destroy() {
        log("Destroying the entity manager factory");
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }

    /**
     * Créée l'instance courante de Entity Manager (liée à ce Thread).
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void createEntityManager() {
        log("Creating the local entity manager");
        threadLocalEntityManager.set(entityManagerFactory.createEntityManager());
    }

    /**
     * Retourne l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau DAO.</strong>
     *
     * @return instance de Entity Manager
     */
    protected static EntityManager getEntityManager() {
        log("Retrieving the local entity manager");
        return threadLocalEntityManager.get();
    }
    
    /**
     * Ferme l'instance courante de Entity Manager (liée à ce Thread).
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void closeEntityManager() {
        log("Closing the local entity manager");
        threadLocalEntityManager.get().close();
        threadLocalEntityManager.set(null);
    }

    /**
     * Démarre une transaction sur l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void openTransaction() {
        log("Beginning a transaction");
        try {
            EntityManager em = threadLocalEntityManager.get();
            em.getTransaction().begin();
        } catch (Exception ex) {
            log("Error beginning a transaction");
            throw ex;
        }
    }

    /**
     * Valide la transaction courante sur l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     *
     * @exception RollbackException lorsque le <em>commit</em> n'a pas réussi.
     */
    public static void commitTransaction() throws RollbackException {
        log("Commiting the transaction");
        try {
            EntityManager em = threadLocalEntityManager.get();
            em.getTransaction().commit();
        } catch (Exception ex) {
            log("Error commiting the transaction");
            throw ex;
        }
    }

    /**
     * Annule la transaction courante sur l'instance courante de Entity Manager.
     * Si la transaction courante n'est pas démarrée, cette méthode n'effectue
     * aucune opération.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void cancelTransaction() {
        try {
            log("Cancelling the transaction");

            EntityManager em = threadLocalEntityManager.get();
            if (em.getTransaction().isActive()) {
                log("Effective cancellation of the transaction");
                em.getTransaction().rollback();
            }
        } catch (Exception ex) {
            log("Error cancelling the transaction");
            throw ex;
        }
    }

}
