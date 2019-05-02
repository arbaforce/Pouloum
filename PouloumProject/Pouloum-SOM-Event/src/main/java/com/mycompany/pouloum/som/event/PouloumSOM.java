/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.event;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.model.Event;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.User;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.dao.DAOEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martin
 */
public class PouloumSOM {
  
    protected DBConnection dBConnection;
    protected JsonObject container;

    public PouloumSOM(JsonObject container) {
        this.container = container;
    }
    
    public void release() {
        this.dBConnection.close();
    }
  
    /**
     * Try to login with a given (nickname, password) pair.
     *
     * @param id is the event id.
     * @return Event, the event matching to the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public Event getEventById(Long id) 
            throws Exception 
    {
        JpaUtil.createEntityManager();
        
        Event e = DAOEvent.findById(id);
        
        JpaUtil.closeEntityManager();
        
        return e;
    }
    
    /**
     * Try to login with a given (nickname, password) pair.
     *
     * @param label is the event name.
     * @param description is the event description.
     * @param startDate is the event starting date.
     * @param duration is the event duration.
     * @param location is the event address.
     * @param activity is the event corresponding activity.
     * @param organizer is the event creator.
     * @param participants_min is the event minimum number of participants.
     * @param participants_max is the event maximum number of participants.
     * @param participants is the event list of User attending to it.
     * @return Event, the event matching to the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public int createEvent(String label, String description, Date startDate, int duration, Address location, Activity activity, User organizer,
            int participants_min, int participants_max, List<User> participants) 
            throws Exception
    {
        Event newEvent = new Event(label, description, startDate, duration, location, activity, organizer,  participants_min, participants_max, participants);
        
        JpaUtil.createEntityManager();

        JpaUtil.openTransaction();

        try {
            DAOEvent.persist(newEvent);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            JpaUtil.cancelTransaction();
        }
        
        JpaUtil.closeEntityManager();
        
        return 0;
    }
    
    
    
    /**
     * Try to login with a given (nickname, password) pair.
     *
     * @param interests, the list containing all activities of the search.
     * @return EventList, a list containing all events and for each event, 
     * the ids of the participants.
     * @throws Exception if there's an error trying to access the database.
     */
    public Map<Event,List<Long>> getEventByInterests(List<Long> interests) 
            throws Exception
    {
        // do magical stuff plz
        return null;
    }

    

    
    
}
