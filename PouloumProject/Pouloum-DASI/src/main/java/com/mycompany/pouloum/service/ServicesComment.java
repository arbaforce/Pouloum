package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOComment;
import com.mycompany.pouloum.dao.DAOEvent;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Comment;
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

public class ServicesComment {

    /**
     * Get a comment, given its id.
     *
     * @param id is the comment id.
     * @return Comment, the comment matching the id.
     */
    public static Comment getCommentById(Long id)
        throws Exception
    {
        JpaUtil.createEntityManager();

        try {
            Comment c = DAOComment.findById(id);
            return c;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Create a comment.
     *
     * @param event is the event.
     * @param author is the comment writer.
     * @param text is the comment content.
     * @param datetime is the comment writing timestamp.
     * @throws Exception if there's an error trying to access the database.
     * @return Comment, the created comment.
     */
    public static Comment createComment(Event event, Pouloumer author, String text, Date datetime)
        throws Exception
    {
        Comment newComment = new Comment(text, datetime, author, event);
        
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOComment.persist(newComment);

                JpaUtil.commitTransaction();
                return newComment;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /*
    
    /**
     * Add a comment to an existing event.
     *
     * @param event is the event.
     * @param pouloumer is the commentor.
     * @param description is the participant to add to the event.
     * @param date is today's date.
     * @throws Exception if there's an error trying to access the database.
     *
    public static void addCommentToEvent(Event event, Pouloumer pouloumer, String description, Date date)
            throws Exception {
        Comment
        
        event.addComment(description, date, pouloumer);

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
     *
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
     * Remove an event from the database.
     *
     * @param id is the id of the event to delete.
     * @throws Exception if there's an error trying to access the database.
     *
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
    
    */

}
