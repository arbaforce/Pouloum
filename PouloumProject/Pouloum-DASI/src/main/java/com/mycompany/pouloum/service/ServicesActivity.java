package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOActivity;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.Badge;
import com.mycompany.pouloum.util.CRE;
import static com.mycompany.pouloum.util.CRE.*;
import java.util.List;

/**
 *
 * @author Martin
 */
public class ServicesActivity {

    /**
     * Get an activity's details, given its id.
     *
     * @param idActivity is the id of the activity.
     * @throws Exception if there's an error trying to access the database, or
     * if there is no activity with the given id.
     * @return Activity, the activity matching the id.
     */
    public static Activity getActivityById(Long idActivity)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Activity a = DAOActivity.findById(idActivity);

            return a;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Create a new activity.
     *
     * @param parent is the parent of the new activity (can be null, which means
     * it will be a root activity).
     * @param name is the name of the new activity.
     * @param description is the name of the new activity.
     * @param badges are the badges associated to the new activity.
     * @param rules are the rules of the new activity.
     * @param resources are the resources for the new activity.
     * @param default_participants_min is the minimal number of participants for
     * the new activity.
     * @param default_participants_max is the maximal nymber of participants for
     * the new activity.
     * @return Activity, the new activity.
     * @throws Exception if something goes wrong when trying to process the
     * transaction.
     */
    public static Activity createActivity(Activity parent, String name, String description, List<Badge> badges, String rules, List<String> resources, int default_participants_min, int default_participants_max)
            throws Exception {
        Activity newActivity = new Activity(parent, name, description, badges, rules, resources, default_participants_min, default_participants_max);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOActivity.persist(newActivity);

                JpaUtil.commitTransaction();
                return newActivity;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Add a child activity to a given activity.
     *
     * @param parent is the parent activity.
     * @param child is the child activity to add to the parent.
     * @return CRE, CRE_OK if everything goes well.
     * @throws Exception if there is an error trying to process the transaction.
     */
    public static CRE addChildActivity(Activity parent, Activity child)
            throws Exception {
        if (child.getParent() != null) {
            return CRE_ERR_ACTIVITY;
        }
        if (parent.getChildren().contains(child)) {
            return CRE_ERR_ACTIVITY;
        }

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                parent.getChildren().add(child);
                child.setParent(parent);

                DAOActivity.updateActivity(parent);
                DAOActivity.updateActivity(child);

                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Get the full list of activities.
     *
     * @return List, a list which contains all the activities.
     * @throws Exception if there's an error trying to access the database.
     */
    public static List<Activity> findAllActivities()
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            List<Activity> availableActivites = DAOActivity.findAll();

            return availableActivites;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

}
