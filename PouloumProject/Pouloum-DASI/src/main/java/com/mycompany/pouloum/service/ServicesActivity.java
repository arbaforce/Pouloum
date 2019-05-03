/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOActivity;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Activity;
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
