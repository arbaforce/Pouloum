package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOPouloumer;
import com.mycompany.pouloum.dao.JpaUtil;
import java.util.Date;
import java.util.List;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.util.CRE;
import static com.mycompany.pouloum.util.CRE.*;
import java.util.ArrayList;

public class ServicesPouloumer {

    /**
     * Try to login with a given (mail, password) pair.
     *
     * @param mail is the mail used to login.
     * @param password is the password of the account.
     * @throws Exception if there's an error trying to access the database.
     * @return Pouloumer, the user matching the credentials or null if they are
     * incorrect.
     */
    public static Pouloumer loginWithMail(String mail, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Pouloumer p = DAOPouloumer.findPouloumerByEmailAndPassword(mail, password);

            return p;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Try to login with a given (nickname, password) pair.
     *
     * @param nickname is the nickname used to login.
     * @param password is the password of the account.
     * @return Pouloumer, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Pouloumer loginWithNickname(String nickname, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Pouloumer p = DAOPouloumer.findPouloumerByNicknameAndPassword(nickname, password);

            return p;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Create a new user account. At this stage, the format and existence of all
     * fields has already been checked by the GUI, so we only need to check if
     * the new user will fit in the DB (no duplicate mail or nickname).
     *
     * @param lastName is the last name of the user.
     * @param firstName is the first name of the user.
     * @param nickname is the nickname of the user.
     * @param mail is the email address of the user.
     * @param password is the password of the user.
     * @param isModerator is whether the user is a moderator (default false).
     * @param isAdmin is whether the user is an adminstrator (default false).
     * @param gender is the gender of the user.
     * @param birthdate is the birthdate of the user.
     * @param phoneNumber is the phone number of the user.
     * @param address is the address of the user.
     * @throws Exception if there's an error trying to access the database.
     * @return CRE, CRE_OK if the registration is successful, CRE_ERR_EMAIL if
     * the email is already used, CRE_ERR_NICKNAME if the nickname is already
     * used.
     */
    public static CRE signUp(String lastName, String firstName, String nickname,
            String mail, String password, boolean isModerator, boolean isAdmin,
            char gender, Date birthdate, String phoneNumber,
            Address address)
            throws Exception {
        Pouloumer p = new Pouloumer(nickname, firstName, lastName, mail,
                password, isModerator, isAdmin, gender, birthdate, phoneNumber,
                address);

        JpaUtil.createEntityManager();

        Pouloumer check = DAOPouloumer.findPouloumerByEmail(mail);
        if (check != null || mail.isEmpty()) {
            // email already used
            return CRE_ERR_EMAIL;
        }
        // email available

        check = DAOPouloumer.findPouloumerByNickname(nickname);
        if (check != null || nickname.isEmpty()) {
            // nickname already used
            return CRE_ERR_NICKNAME;
        }
        // nickname available

        if (password.length() < 8) {
            // password too weak
            return CRE_ERR_PASSWORD;
        }
        // password not too weak

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.persist(p);

                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                // Registration has failed
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }

        return CRE_OK;
    }

    /**
     * Update an account. At this stage, the format and existence of all fields
     * has already been checked by the GUI, so we only need to check if the
     * modified fields will fit in the DB (no duplicate mail or nickname).
     *
     * @param p is the user to update.
     * @param lastName is the last name of the user.
     * @param firstName is the first name of the user.
     * @param nickname is the nickname of the user.
     * @param email is the email address of the user.
     * @param password is the password of the user.
     * @param isModerator is whether the user is a moderator (default false).
     * @param isAdmin is whether the user is an adminstrator (default false).
     * @param gender is the gender of the user.
     * @param birthdate is the birthdate of the user.
     * @param phoneNumber is the phone number of the user.
     * @param address is the address of the user.
     * @throws Exception if there's an error trying to access the database.
     * @return CRE, CRE_OK if the update is successful, CRE_ERR_EMAIL if the new
     * email is already used, CRE_ERR_NICKNAME if the new nickname is already
     * used.
     */
    public static CRE updatePouloumer(Pouloumer p, String lastName, String firstName, String nickname,
            String email, String password, boolean isModerator, boolean isAdmin, char gender, Date birthdate, String phoneNumber,
            Address address)
            throws Exception {

        JpaUtil.createEntityManager();

        if (!email.equals(p.getEmail())) {
            Pouloumer check = DAOPouloumer.findPouloumerByEmail(email);
            if (check != null || email.isEmpty()) {
                // email already used
                return CRE_ERR_EMAIL;
            }
            // email available
        }

        if (!nickname.equals(p.getNickname())) {
            Pouloumer check = DAOPouloumer.findPouloumerByNickname(nickname);
            if (check != null || nickname.isEmpty()) {
                // nickname already used
                return CRE_ERR_NICKNAME;
            }
            // nickname available
        }

        if (password.length() < 8) {
            // password too weak
            return CRE_ERR_PASSWORD;
        }
        // password not too weak

        // Update fields
        p.setLast_name(lastName);
        p.setFirst_name(firstName);
        p.setNickname(nickname);
        p.setEmail(email);
        p.setPassword(password);
        p.setModerator(isModerator);
        p.setAdministrator(isAdmin);
        p.setGender(gender);
        p.setBirth_date(birthdate);
        p.setPhone_number(phoneNumber);
        p.setAddress(address);

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.updatePouloumer(p);
                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                // Registration has failed
                JpaUtil.cancelTransaction();
                throw ex;
            }

        } finally {
            JpaUtil.closeEntityManager();
        }

        return CRE_OK;
    }

    /**
     * Get a user given their id.
     *
     * @param id is the id of the user.
     * @throws Exception if there's an error trying to access the database, or
     * if there is no user with the given id.
     * @return Pouloumer, the user matching the given id.
     */
    public static Pouloumer getPouloumerById(Long id)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Pouloumer p = DAOPouloumer.findById(id);

            return p;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Get a user given their e-mail address.
     *
     * @param mail is the e-mail address of the user.
     * @throws Exception if there's an error trying to access the database.
     * @return Pouloumer, the user matching the given address, or null if there
     * is none.
     */
    public static Pouloumer getPouloumerByEmail(String mail)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Pouloumer p = DAOPouloumer.findPouloumerByEmail(mail);

            return p;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

    /**
     * Get a user given their nickname.
     *
     * @param nickname is the nickname of the user.
     * @throws Exception if there's an error trying to access the database.
     * @return Pouloumer, the user matching the given nickname, or null if there
     * is none.
     */
    public static Pouloumer getPouloumerByNickname(String nickname)
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            Pouloumer p = DAOPouloumer.findPouloumerByNickname(nickname);

            return p;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
    public static List<Event> getPouloumerEvents(Pouloumer p, boolean history) {
        List<Event> events = new ArrayList<>();
        
        for (Event e : p.getEvents()) {
            if (history && (e.isFinished())) { // Looking for events history
                events.add(e);
            } 
            if (!history && !(e.isFinished() || e.isCancelled())) {
                events.add(e);
            }
        }
        
        return events;
    }

    /**
     * Check if the user is going to attend events that overlapse a given event.
     *
     * @param p is the user.
     * @param e is the event prone to creating overlap conflicts.
     * @return CRE, CRE_OK if everything is fine, CRE_ERR_EVENT_AT_SAME_TIME if
     * there's an overlapping conflict, CRE_WAR_EVENT_NEAR if the new event is
     * close to another event the user has already joined.
     */
    public static CRE checkForOverlapsingEvents(Pouloumer p, Event e) {
        List<Event> events = p.getEvents();

        boolean warning = false;

        for (Event pouloumerEvent : events) {
            if (pouloumerEvent.overlaps(e)) {
                // There can only be one such conflict at a time as we will immediately demand it to be resolved, so we can return
                return CRE_ERR_EVENT_AT_SAME_TIME;
            } else if (pouloumerEvent.isNear(e)) {
                warning = true;
            }
        }

        if (warning) { // If one (or more) other events is less than 2 hours away from the event to join, send a warning
            return CRE_WAR_EVENT_NEAR;
        }

        return CRE_OK;
    }

    /**
     * Add an event to a user's event list.
     *
     * @param p is the user joining the event.
     * @param event is the event to join.
     * @return CRE, CRE_OK if the update is successful, CRE_ERR_EVENT if the
     * event to join is already full.
     * @throws Exception if there's an error trying to access the database.
     */
    public static CRE joinEvent(Pouloumer p, Event event)
            throws Exception {

        if (event.isFull()) {
            return CRE_ERR_EVENT;
        }

        p.addEvent(event);
        
        System.out.println(event.getId());
        
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.updatePouloumer(p);
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
     * Remove an event from a user's event list.
     *
     * @param p is the user leaving the event.
     * @param event is the event to leave.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void leaveEvent(Pouloumer p, Event event)
            throws Exception {
        p.removeEvent(event);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.updatePouloumer(p);

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
     * Add activites to a user's interests. There is no need to check whether
     * the interests to add are already linked to the user's profile as already
     * linked interests will not be selectable in the GUI.
     *
     * @param p is the user to which we add interests.
     * @param interests is the list of interests to add.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void addInterests(Pouloumer p, Activity interest)
            throws Exception {
        p.getInterests().add(interest);

        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.updatePouloumer(p);

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
     * Remove an activity from a user's interests.
     *
     * @param p is the user from which we remove the interest.
     * @param interest is the interest to remove.
     * @throws Exception if there's an error trying to access the database.
     */
    public static void removeInterest(Pouloumer p, Activity interest)
            throws Exception {
                
        p.removeInterest(interest);
                
        JpaUtil.createEntityManager();

        try {
            JpaUtil.openTransaction();

            try {
                DAOPouloumer.updatePouloumer(p);

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
     * Get the full list of pouloumers.
     *
     * @return List, a list which contains all the pouloumers.
     * @throws Exception if there's an error trying to access the database.
     */
    public static List<Pouloumer> findAllPouloumers()
            throws Exception {
        JpaUtil.createEntityManager();

        try {
            List<Pouloumer> pouloumers = DAOPouloumer.findAll();

            return pouloumers;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }

}
