/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.user;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.dao.DAOUser;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Event;
import com.mycompany.pouloum.model.User;
import java.util.Date;
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
     * Try to login with a given (mail, password) pair.
     *
     * @param mail is the mail used to login.
     * @param password is the password of the account.
     * @return User, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public User loginWithMail(String mail, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        User u = DAOUser.findUserByEmailAndPassword(mail, password);

        JpaUtil.closeEntityManager();

        return u;
    }

    /**
     * Try to login with a given (nickname, password) pair.
     *
     * @param nickname is the nickname used to login.
     * @param password is the password of the account.
     * @return User, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public User loginWithNickname(String nickname, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        User u = DAOUser.findUserByNicknameAndPassword(nickname, password);

        JpaUtil.closeEntityManager();

        return u;
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
     * @return int, 0 if the registration is successful, 1 if the email is
     * already used, 2 if the nickname is already used, 3 if there was an error
     * when trying to process the transaction.
     */
    public int signUp(String lastName, String firstName, String nickname,
            String mail, String password, boolean isModerator, boolean isAdmin, char gender, Date birthdate, String phoneNumber,
            Address address) throws Exception {
        User u = new User(nickname, firstName, lastName, mail, password, isModerator, isAdmin, gender, birthdate, phoneNumber, address);

        JpaUtil.createEntityManager();

        User check = DAOUser.findUserByEmail(mail);
        if (check != null) // email already used
        {
            return 1;
        }
        // email available

        check = DAOUser.findUserByNickname(nickname);
        if (check != null) // nickname already used
        {
            return 2;
        }
        // nickname available

        JpaUtil.openTransaction();

        try {
            DAOUser.persist(u);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            // Registration has failed, return null to let the GUI know
            JpaUtil.cancelTransaction();
        }

        JpaUtil.closeEntityManager();

        return 0;
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
     * @param mail is the email address of the user.
     * @param password is the password of the user.
     * @param isModerator is whether the user is a moderator (default false).
     * @param isAdmin is whether the user is an adminstrator (default false).
     * @param gender is the gender of the user.
     * @param birthdate is the birthdate of the user.
     * @param phoneNumber is the phone number of the user.
     * @param address is the address of the user.
     * @return int, 0 if the update is successful, 1 if the new email is already
     * used, 2 if the new nickname is already used, 3 if there was an error when
     * trying to process the transaction.
     */
    public int updateUser(User p, String lastName, String firstName, String nickname,
            String mail, String password, boolean isModerator, boolean isAdmin, char gender, Date birthdate, String phoneNumber,
            Address address) throws Exception {
        
        // Update fields
        p.setLast_name(lastName);
        p.setFirst_name(firstName);
        p.setNickname(nickname);
        p.setEmail(mail);
        p.setPassword(password);
        p.setModerator(isModerator);
        p.setAdministrator(isAdmin);
        p.setGender(gender);
        p.setBirth_date(birthdate);
        p.setPhone_number(phoneNumber);
        p.setAddress(address);
        

        JpaUtil.createEntityManager();

        User check = DAOUser.findUserByEmail(mail);
        if (check != null) // email already used
        {
            return 1;
        }
        // email available

        check = DAOUser.findUserByNickname(nickname);
        if (check != null) // nickname already used
        {
            return 2;
        }
        // nickname available

        JpaUtil.openTransaction();

        try {
            DAOUser.persist(p);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            // Registration has failed, return null to let the GUI know
            JpaUtil.cancelTransaction();
        }

        JpaUtil.closeEntityManager();

        return 0;
    }

    /**
     * Get a user given their id.
     *
     * @param id is the id of the user.
     * @return User, the user matching the given id, or null if there is
     * none.
     */
    public User getUserById(Long id) throws Exception {
        JpaUtil.createEntityManager();

        User u = DAOUser.findById(id);

        JpaUtil.closeEntityManager();

        return u;
    }

    /**
     * Get a user given their e-mail address.
     *
     * @param mail is the e-mail address of the user.
     * @return User, the user matching the given address, or null if there
     * is none.
     */
    public User getUserByEmail(String mail) throws Exception {
        JpaUtil.createEntityManager();

        User u = DAOUser.findUserByEmail(mail);

        JpaUtil.closeEntityManager();

        return u;
    }

    /**
     * Get a user given their nickname.
     *
     * @param nickname is the nickname of the user.
     * @return User, the user matching the given nickname, or null if there
     * is none.
     */
    public User getUserByNickname(String nickname) throws Exception {
        JpaUtil.createEntityManager();

        User u = DAOUser.findUserByNickname(nickname);

        JpaUtil.closeEntityManager();

        return u;
    }

    /**
     * Remove an event from a user's event list.
     *
     * @param p is the user leaving the event.
     * @param event is the event to leave.
     * @return int, 0 if the update was successful, 1 if there was a problem
     * updating the database.
     */
    public int leaveEvent(User p, Event event) {
        p.getEvents().remove(event);

        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();

        try {
            DAOUser.updateUser(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            return 1;
        }

        JpaUtil.closeEntityManager();

        return 0;
    }

    /**
     * Add activites to a user's interests. There is no need to check whether
     * the interests to add are already linked to the user's profile as already
     * linked interests will not be selectable in the GUI.
     *
     * @param p is the user to which we add interests.
     * @param interests is the list of interests to add.
     * @return 0 if the update was successful, 1 if there was a problem updating
     * the database.
     */
    public int addInterests(User p, List<Activity> interests) {
        p.getInterests().addAll(interests);

        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();

        try {
            DAOUser.updateUser(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            return 1;
        }

        JpaUtil.closeEntityManager();

        return 0;
    }

    /**
     * Remove an activity from a user's interests.
     *
     * @param p is the user from which we remove the interest.
     * @param interest is the interest to remove.
     * @return 0 if the update was successful, 1 if there was a problem updating
     * the database.
     */
    public int rmeoveInterest(User p, Activity interest) {
        p.getInterests().remove(interest);

        JpaUtil.createEntityManager();
        JpaUtil.openTransaction();

        try {
            DAOUser.updateUser(p);
            JpaUtil.commitTransaction();
        } catch (Exception e) {
            JpaUtil.cancelTransaction();
            return 1;
        }

        JpaUtil.closeEntityManager();

        return 0;
    }

}
