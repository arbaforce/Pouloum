/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.user;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.dao.DAOPouloumer;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Pouloumer;
import java.util.Date;

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
     */
    public Pouloumer loginMail(String mail, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        Pouloumer u = DAOPouloumer.findUserByEmailAndPassword(mail, password);

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
     */
    public Pouloumer loginNickname(String nickname, String password)
            throws Exception {
        JpaUtil.createEntityManager();

        Pouloumer u = DAOPouloumer.findUserByNicknameAndPassword(nickname, password);

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
     * @param address is
     * @return int, 0 if the registration is successful, 1 if the email is
     * already used, 2 if the nickname is already used, 3 if there was an error
     * when trying to process the transaction.
     */
    public int signUp(String lastName, String firstName, String nickname,
            String mail, String password, boolean isModerator, boolean isAdmin, char gender, Date birthdate, String phoneNumber,
            Address address)
        throws Exception
    {
        Pouloumer u = new Pouloumer(nickname, firstName, lastName, mail, password, isModerator, isAdmin, gender, birthdate, phoneNumber, address);

        JpaUtil.createEntityManager();

        Pouloumer check = DAOPouloumer.findUserByEmail(mail);
        if (check != null) // email already used
        {
            return 1;
        }
        // email available

        check = DAOPouloumer.findUserByNickname(nickname);
        if (check != null) // nickname already used
        {
            return 2;
        }
        // nickname available

        JpaUtil.openTransaction();

        try {
            DAOPouloumer.persist(u);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            // Registration has failed, return null to let the GUI know
            JpaUtil.cancelTransaction();
        }

        return 0;
    }

}
