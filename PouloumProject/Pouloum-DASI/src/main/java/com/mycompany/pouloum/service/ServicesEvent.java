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
    public static Event getEventById(Long id)
        throws Exception
    {
        JpaUtil.createEntityManager();

        try {
            Event e = DAOEvent.findById(id);
            return e;
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
     * @throws Exception if there's an error trying to access the database.
     * @return Event, the event matching to the id.
     */
    public static Event createEvent(String label, String description, Date startDate, int duration, Address location, Activity activity, Pouloumer organizer,
            int participants_min, int participants_max)
        throws Exception
    {
        Event newEvent = new Event(label, description, startDate, false, duration, location, activity, organizer, participants_min, participants_max);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.persist(newEvent);

                JpaUtil.commitTransaction();
                return newEvent;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
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
     * @throws Exception if there's an error trying to access the database.
     * @return CRE, CRE_OK if the registration is successful, CRE_ERR_POULOUMER
     * if the pouloumer is already participating in the event.
     */
    public static CRE addParticipant(Event event, Pouloumer newParticipant)
        throws Exception
    {
        if (event.getParticipants().contains(newParticipant)) {
            return CRE_ERR_POULOUMER;
        }
        /*
        for (Pouloumer p : event.getParticipants()) {
            if (p.getId().equals(newParticipant.getId())) {
                return CRE_ERR_POULOUMER;
            }
        }
        */
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
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Add a comment to an existing event.
     *
     * @param event is the event.
     * @param participant is the commentor.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void removeParticipant(Event event, Pouloumer participant)
        throws Exception
    {
        event.removeParticipant(participant);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(event);
                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Add a comment to an existing event.
     *
     * @param event is the event.
     * @param pouloumer is the commentor.
     * @param description is the participant to add to the event.
     * @param date is today's date.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void addCommentToEvent(Event event, Pouloumer pouloumer, String description, Date date)
            throws Exception {
        event.addComment(description, date, pouloumer.getId());

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(event);

                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Update an existing event (should only be done by its organizer).
     *
     * @param event, the event to update.
     * @param date, the new date of the event.
     * @param duration, the new duration of the event.
     * @param address, the new address of the event.
     * @param playerMin, the new minimum number of participants of the event.
     * @param playerMax, the new maximum number of participants of the event.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void updateEvent(Event event, Date date, int duration, Address address, int playerMin, int playerMax)
            throws Exception {
        
        // Update fields
        event.setStart(date);
        event.setDuration(duration);
        event.setLocation(address);
        event.setParticipants_min(playerMin);
        event.setParticipants_max(playerMax);
        
        JpaUtil.createEntityManager();
        
        try {
            JpaUtil.openTransaction();
            
            try {
                DAOEvent.updateEvent(event);
                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }
            
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Set an event's state to cancelled.
     *
     * @param event is the event to cancel
     * @throws Exception if there's an error trying to access the database.
     */
    public static void cancelEvent(Event event) throws Exception {
        event.setCancelled(true);
        
        //TODO notify participants that event has been cancelled
        
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.updateEvent(event);
                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }

    }

    /**
     * Get the events matching a list of interests.
     *
     * @param interests, the list containing all activities of the search.
     * @throws Exception if there's an error trying to access the database.
     * @return EventList, a list containing all events corresponding to the
     * interests and for each event, the the participants.
     */
    public static Map<Event, List<Pouloumer>> getEventByInterests(List<Activity> interests)
        throws Exception
    {
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
     * @throws Exception if there's an error trying to access the database.
     */
    public static void deleteEvent(Long id)
        throws Exception
    {
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOEvent.removeById(id);

                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Get the list of events organized by a given user.
     *
     * @param idPouloumer is the user organizing the events we look for.
     * @throws Exception if there's an error trying to access the database.
     * @return List, a list of events organized by p.
     */
    public static List<Event> getOrganizedEvents(Long idPouloumer)
        throws Exception
    {
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
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
}
