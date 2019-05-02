/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.activity;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.dao.DAOActivity;
import java.util.List;



/**
 *
 * @author Martin
 */
public class PouloumSOM {
  
    protected DBConnection dBConnection;
    protected JsonObject container;

    public PouloumSOM(DBConnection dBConnection, JsonObject container) {
        this.dBConnection = dBConnection;
        this.container = container;
    }
    
    public void release() {
        this.dBConnection.close();
    }

    /**
     * Get an activity details
     *
     * @param idActivity is the id of the activity.
     * @return Activity, the activity matching the id.
     */
    public Activity getActivityById(Long idActivity) 
            throws Exception 
    {
        JpaUtil.createEntityManager();

        Activity a = DAOActivity.findById(idActivity);

        JpaUtil.closeEntityManager();

        return a;
    }
    
    /**
     * Get the full list of activities.
     *
     * @return availableActivities, which contains all the activities.
     */
    public List<Activity> findAllActivities() 
            throws Exception 
    {
        JpaUtil.createEntityManager();
        
        List<Activity> availableActivites = DAOActivity.findAll();
        
        JpaUtil.closeEntityManager();

        return availableActivites;
    }    
    
}
