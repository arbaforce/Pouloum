/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOEvent;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Event;
import com.mycompany.pouloum.model.Pouloumer;
import com.mycompany.pouloum.util.CRE;
import static com.mycompany.pouloum.util.CRE.*;
import com.mycompany.pouloum.util.exception.DBException;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martin
 */
public class ServicesEvent {

    /**
     * Get an event, given its id.
     *
     * @param id is the event id.
     * @return Event, the event matching to the id.
     */
    public static Event getEventById(Long id) {
        JpaUtil.createEntityManager();

        try {
            Event e = DAOEvent.findById(id);
            return e;
        } catch (Exception e) {
            return null;
        } finally {
            JpaUtil.closeEntityManager();
        }
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
     * @return Event, the event matching to the id.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE createEvent(String label, String description, Date startDate, int duration, Address location, Activity activity, Pouloumer organizer,
            int participants_min, int participants_max)
            throws Exception {
        Event newEvent = new Event(label, description, startDate, false, duration, location, activity, organizer, participants_min, participants_max);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.persist(newEvent);

                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw new DBException("ERROR : Database could not persist entity Event", ex);
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Add a participant to an event.
     *
     * @param newParticipant is the participant to add to the event.
     * @param event is the event.
     * @return CRE, CRE_OK if the registration is successful, CRE_ERR_EVENT if
     * the event does not exist, CRE_ERR_POULOUMER if the pouloumer is already
     * participating in the event, CRE_EXC_BD if the transaction has been
     * canceled.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE addParticipant(Pouloumer newParticipant, Event event)
            throws Exception {
        //TODO where is the event's existence checked ?
        /*boolean isAlreadyParticipating = false;*/
        for (Pouloumer p : event.getParticipants()) {
            if (p.getId().equals(newParticipant.getId())) {
                /*isAlreadyParticipating = true;
                break;*/
                return CRE_ERR_POULOUMER;
            }
        }
        /*if (isAlreadyParticipating) {
            throw new ServiceException("ERROR : participant is already attending to this event");
        }*/
        event.addParticipant(newParticipant);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(event);

                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    public static CRE removeParticipant(Pouloumer participant, Event event)
            throws Exception {
        event.removeParticipant(participant);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(event);
                JpaUtil.commitTransaction();
                return CRE.CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE.CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Add a comment to an existing event.
     *
     * @param description is the participant to add to the event.
     * @param idEvent is the id of the event.
     * @param date is today's date.
     * @param idPouloumer is the id of the commentor.
     * @return CRE, CRE_OK if the registration is successful, CRE_ERR_EVENT if
     * the event does not exist, CRE_EXC_BD if the transaction is canceled.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE addCommentToEvent(String description, Date date, Long idEvent, Long idPouloumer)
            throws Exception {
        Event e = DAOEvent.findById(idEvent);
        if (e == null) {
            return CRE_ERR_EVENT;
        }

        e.addComment(description, date, idPouloumer);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(e);

                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Update an existing event(should only be updated by the creator of the
     * event).
     *
     * @param event, the event to update.
     * @param date, the new date of the event.
     * @param duration, the new duration of the event.
     * @param address, the new address of the event.
     * @param playerMin, the new minimum number of participants of the event.
     * @param playerMax, the new maximum number of participants of the event.
     * @return CRE, CRE_OK if the update is successful, CRE_ERR_EVENT if the
     * event does not exist, CRE_EXC_BD if the transaction is canceled.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE updateEvent(Event event, Date date, int duration, Address address, int playerMin, int playerMax)
            throws Exception {

        Event e = DAOEvent.findById(event.getId());

        if (e != event) {
            return CRE_ERR_EVENT;
        }

        // Update fields
        e.setStart(date);
        e.setDuration(duration);
        e.setLocation(address);
        e.setParticipants_min(playerMin);
        e.setParticipants_max(playerMax);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.persist(e);
                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Set an event's state to cancelled.
     *
     * @param event is the event to cancel
     * @return CRE, CRE_OK if the update is successful, CRE_ERR_EVENT if the
     * event does not exist, CRE_EXC_BD if the transaction is canceled.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE cancelEvent(Event event) throws Exception {
        Event e = DAOEvent.findById(event.getId());

        if (e != event) {
            return CRE_ERR_EVENT;
        }

        e.setCancelled(true);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.persist(e);
                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }

    }

    /**
     * Get the events matching a list of interests.
     *
     * @param interests, the list containing all activities of the search.
     * @return EventList, a list containing all events corresponding to the
     * interests and for each event, the the participants.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Map<Event, List<Pouloumer>> getEventByInterests(List<Activity> interests)
            throws Exception {
        // do magical stuff plz
        JpaUtil.createEntityManager();

        try {
            List<Event> events = DAOEvent.findAll();

            Map<Event, List<Pouloumer>> interestsEvents = new HashMap<>();

            for (Event e : events) {
                if (interests.contains(e.getActivity()) || interests.isEmpty()) {
                    List<Pouloumer> participants = e.getParticipants();

                    interestsEvents.put(e, participants);
                }
            }

            return interestsEvents;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Remove an event from the database.
     *
     * @param id is the id of the event to delete.
     * @return CRE, CRE_OK if the deletion is successful, CRE_EXC_BD if there
     * was an error trying to access the database.
     */
    public static CRE deleteEvent(Long id) {
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.removeById(id);

                JpaUtil.commitTransaction();
                return CRE_OK;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                return CRE_EXC_BD;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Get the list of events organized by a given user.
     *
     * @param idPouloumer is the user organizing the events we look for.
     * @return List, a list of events organized by p.
     */
    public static List<Event> getOrganizedEvents(Long idPouloumer) {
        JpaUtil.createEntityManager();

        try {
            List<Event> answer = new ArrayList<>();
            List<Event> allEvents = DAOEvent.findAll();

            for (Event e : allEvents) {
                if (e.getOrganizer().getId().equals(idPouloumer)) {
                    answer.add(e);
                }
            }

            return answer;

        } catch (Exception e) {
            return null;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
}
