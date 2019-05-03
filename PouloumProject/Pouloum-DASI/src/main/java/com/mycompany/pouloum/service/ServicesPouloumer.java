package com.mycompany.pouloum.service;

import com.google.maps.model.LatLng;
import com.mycompany.pouloum.dao.DAOEvent;
import com.mycompany.pouloum.dao.DAOPouloumer;
import com.mycompany.pouloum.dao.JpaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.util.CRE;
import static com.mycompany.pouloum.util.CRE.*;
import com.mycompany.pouloum.util.GeoTest;
import com.mycompany.pouloum.util.exception.ServiceException;

public class ServicesPouloumer {
    
    /**
     * Try to login with a given (mail, password) pair.
     *
     * @param mail is the mail used to login.
     * @param password is the password of the account.
     * @return Pouloumer, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Pouloumer loginWithMail(String mail, String password)
            throws Exception {
        JpaUtil.createEntityManager();
        
        Pouloumer p = DAOPouloumer.findPouloumerByEmailAndPassword(mail, password);
        
        JpaUtil.closeEntityManager();
        
        return p;
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
        
        Pouloumer p = DAOPouloumer.findPouloumerByNicknameAndPassword(nickname, password);
        
        JpaUtil.closeEntityManager();
        
        return p;
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
     * @return CRE, CRE_OK if the registration is successful, CRE_ERR_EMAIL if
     * the email is already used, CRE_ERR_NICKNAME if the nickname is already
     * used, CRE_EXC_BD if there was an error when trying to process the
     * transaction.
     */
    public static CRE signUp(String lastName, String firstName, String nickname,
            String mail, String password, boolean isModerator, boolean isAdmin, 
            char gender, Date birthdate, String phoneNumber,
            Address address) throws Exception {
        
        Pouloumer p = new Pouloumer(nickname, firstName, lastName, mail, 
                password, isModerator, isAdmin, gender, birthdate, phoneNumber, 
                address);
        
        JpaUtil.createEntityManager();
        
        Pouloumer check = DAOPouloumer.findPouloumerByEmail(mail);
        if (check != null) // email already used
        {
            throw new ServiceException("email is already used");
        }
        // email available
        
        check = DAOPouloumer.findPouloumerByNickname(nickname);
        if (check != null) // nickname already used
        {
            throw new ServiceException("nickname is already used");
        }
        // nickname available
        
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.persist(p);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            // Registration has failed, return null to let the GUI know
            JpaUtil.cancelTransaction();
            throw ex;
        }
        
        JpaUtil.closeEntityManager();
        
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
     * @return CRE, CRE_OK if the update is successful, CRE_ERR_EMAIL if the new
     * email is already used, CRE_ERR_NICKNAME if the new nickname is already
     * used, CRE_EXC_BD if there was an error when trying to process the
     * transaction.
     */
    public static CRE updatePouloumer(Pouloumer p, String lastName, String firstName, String nickname,
            String email, String password, boolean isModerator, boolean isAdmin, char gender, Date birthdate, String phoneNumber,
            Address address) throws Exception {
        
        JpaUtil.createEntityManager();
        
        if (email != p.getEmail()) {
            Pouloumer check = DAOPouloumer.findPouloumerByEmail(email);
            if (check != null) // email already used
            {
                return CRE_ERR_EMAIL;
            }
            // email available
        }
        
        if (nickname != p.getNickname()) {
            Pouloumer check = DAOPouloumer.findPouloumerByNickname(nickname);
            if (check != null) // nickname already used
            {
                return CRE_ERR_NICKNAME;
            }
            // nickname available
        }
        
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
        
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.persist(p);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            // Registration has failed, return null to let the GUI know
            JpaUtil.cancelTransaction();
            throw ex;
        }
        JpaUtil.closeEntityManager();
        
        return CRE_OK;
    }
    
    /**
     * Get a user given their id.
     *
     * @param id is the id of the user.
     * @return Pouloumer, the user matching the given id, or null if there is
     * none.
     */
    public static Pouloumer getPouloumerById(Long id) throws Exception {
        JpaUtil.createEntityManager();
        
        Pouloumer p = DAOPouloumer.findById(id);
        
        JpaUtil.closeEntityManager();
        
        return p;
    }
    
    /**
     * Get a user given their e-mail address.
     *
     * @param mail is the e-mail address of the user.
     * @return Pouloumer, the user matching the given address, or null if there
     * is none.
     */
    public static Pouloumer getPouloumerByEmail(String mail) throws Exception {
        JpaUtil.createEntityManager();
        
        Pouloumer p = DAOPouloumer.findPouloumerByEmail(mail);
        
        JpaUtil.closeEntityManager();
        
        return p;
    }
    
    /**
     * Get a user given their nickname.
     *
     * @param nickname is the nickname of the user.
     * @return Pouloumer, the user matching the given nickname, or null if there
     * is none.
     */
    public static Pouloumer getPouloumerByNickname(String nickname) throws Exception {
        JpaUtil.createEntityManager();
        
        Pouloumer p = DAOPouloumer.findPouloumerByNickname(nickname);
        
        JpaUtil.closeEntityManager();
        
        return p;
    }
    
    public static CRE joinEvent(Pouloumer p, Event event) {
        List<Event> events = p.getEvents();
        events.add(event);
        p.setEvents(events);
        
        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.updatePouloumer(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            JpaUtil.closeEntityManager();
            return CRE_EXC_BD;
        }
        
        JpaUtil.closeEntityManager();
        
        return CRE_OK;
    }
    
    /**
     * Remove an event from a user's event list.
     *
     * @param p is the user leaving the event.
     * @param event is the event to leave.
     * @return CRE, CRE_OK if the update was successful, CRE_EXC_BD if there was
     * a problem updating the database.
     */
    public static CRE leaveEvent(Pouloumer p, Event event) {
        p.removeEvent(event);
        
        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.updatePouloumer(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            JpaUtil.closeEntityManager();
            return CRE_EXC_BD;
        }
        
        JpaUtil.closeEntityManager();
        
        return CRE_OK;
    }
    
    /**
     * Add activites to a user's interests. There is no need to check whether
     * the interests to add are already linked to the user's profile as already
     * linked interests will not be selectable in the GUI.
     *
     * @param p is the user to which we add interests.
     * @param interests is the list of interests to add.
     * @return CRE, CRE_OK if the update was successful, CRE_EXC_BD if there
     * was a problem updating the database.
     */
    public static CRE addInterests(Pouloumer p, List<Activity> interests) {
        p.getInterests().addAll(interests);
        
        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.updatePouloumer(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            JpaUtil.closeEntityManager();
            return CRE_EXC_BD;
        }
        
        JpaUtil.closeEntityManager();
        
        return CRE_OK;
    }
    
    /**
     * Remove an activity from a user's interests.
     *
     * @param p is the user from which we remove the interest.
     * @param interest is the interest to remove.
     * @return CRE_OK if the update was successful, CRE_EXC_BD if there was a
     * problem updating the database.
     */
    public static CRE removeInterest(Pouloumer p, Activity interest) {
        p.getInterests().remove(interest);
        
        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();
        
        try {
            DAOPouloumer.updatePouloumer(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            JpaUtil.closeEntityManager();
            return CRE_EXC_BD;
        }
        
        JpaUtil.closeEntityManager();
        
        return CRE_OK;
    }
    
}
