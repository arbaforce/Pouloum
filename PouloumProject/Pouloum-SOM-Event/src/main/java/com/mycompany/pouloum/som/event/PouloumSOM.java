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
import com.mycompany.pouloum.model.Pouloumer;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.dao.DAOEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Get an event, given its id.
     *
     * @param id is the event id.
     * @return Event, the event matching to the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public Event getEventById(Long id)
            throws Exception {
        JpaUtil.createEntityManager();

        Event e = DAOEvent.findById(id);

        JpaUtil.closeEntityManager();

        return e;
    }

    /**
     * Create an event.
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
     * @param participants is the event list of Pouloumer attending to it.
     * @return Event, the event matching to the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public int createEvent(String label, String description, Date startDate, int duration, Address location, Activity activity, Pouloumer organizer,
            int participants_min, int participants_max, List<Pouloumer> participants)
            throws Exception {
        Event newEvent = new Event(label, description, startDate, duration, location, activity, organizer, participants_min, participants_max, participants);

        JpaUtil.createEntityManager();

        JpaUtil.openTransaction();

        try {
            DAOEvent.persist(newEvent);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            JpaUtil.cancelTransaction();
            JpaUtil.closeEntityManager();
            return 1;
        }

        JpaUtil.closeEntityManager();

        return 0;
    }

    /**
     * Add a participant to an event.
     *
     * @param newParticipant is the participant to add to the event.
     * @param idEvent is the id of the event.
     * @return int 0 if the registration is successful, 1 if the event does not
     * exist, 2 if the pouloumer is already participating in the event.
     * @throws Exception if there's an error trying to access the database.
     */
    public int addParticipant(Pouloumer newParticipant, Long idEvent)
            throws Exception {
        Event e = DAOEvent.findById(idEvent);
        if (e == null) {
            return 1;
        }
        if (e.getParticipants().contains(newParticipant)) {
            return 2;
        }
        e.addParticipant(newParticipant);
        return 0;
    }

    /**
     * Get the events matching a list of interests.
     *
     * @param interests, the list containing all activities of the search.
     * @return EventList, a list containing all events and for each event, the
     * ids of the participants.
     * @throws Exception if there's an error trying to access the database.
     */
    public Map<Event, List<Long>> getEventByInterests(List<Long> interests)
            throws Exception {
        // do magical stuff plz
        return null;
    }

    /**
     * Remove an event from the database.
     *
     * @param id is the id of the event to delete.
     * @return 0 if the deletion is successful, 1 if there was an error trying
     * to access the database.
     */
    public int deleteEvent(Long id) {
        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();

        try {
            DAOEvent.removeById(id);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            JpaUtil.cancelTransaction();
            return 1;
        }

        return 0;
    }
    
    public List<Event> getOrganizedEvents(Pouloumer p) throws Exception {
        JpaUtil.createEntityManager();
        
        List<Event> answer = new ArrayList<>();
        List<Event> allEvents = DAOEvent.findAll();
        
        for (Event e : allEvents) {
            if (e.getOrganizer().getId() == p.getId()) {
                answer.add(e);
            }
        }
        
        return answer;
    }

}
