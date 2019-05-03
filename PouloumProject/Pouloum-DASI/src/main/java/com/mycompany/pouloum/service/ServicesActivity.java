/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * Get an activity details
     *
     * @param idActivity is the id of the activity.
     * @return Activity, the activity matching the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Activity getActivityById(Long idActivity) 
            throws Exception 
    {
        JpaUtil.createEntityManager();
        
        try {
            Activity a = DAOActivity.findById(idActivity);
            
            return a;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
    public static Activity createActivity(Activity parent, List<Activity> children, String name, String description, List<Badge> badges, String rules, List<String> resources, int default_participants_min, int default_participants_max)
        throws Exception
    {
        Activity newActivity = new Activity(parent, children, name, description, badges, rules, resources, default_participants_min, default_participants_max);
        
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

    public static CRE addActivityChild(Activity parent, Activity child)
        throws Exception
    {
        if (child.getParent() != null) return CRE_ERR_ACTIVITY;
        if (parent.getChildren().contains(child)) return CRE_ERR_ACTIVITY;
        
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
     * @return availableActivities, which contains all the activities.
     * @throws Exception if there's an error trying to access the database.
     */
    public static List<Activity> findAllActivities() 
            throws Exception 
    {
        JpaUtil.createEntityManager();
        
        try {
            List<Activity> availableActivites = DAOActivity.findAll();
            
            return availableActivites;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }    
    
}
