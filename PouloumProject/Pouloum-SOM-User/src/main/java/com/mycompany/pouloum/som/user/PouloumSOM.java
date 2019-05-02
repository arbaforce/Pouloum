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
import com.mycompany.pouloum.model.User;

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
    public User loginMail(String mail, String password)
        throws Exception
    {
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
     */
    public User loginNickname(String nickname, String password)
        throws Exception
    {
        JpaUtil.createEntityManager();

        User u = DAOUser.findUserByNicknameAndPassword(nickname, password);

        JpaUtil.closeEntityManager();

        return u;
    }

    public User signUp(String lastName, String firstName, String nickname,
            String mail, String password, char gender, Date birthday, String phoneNumber,
            Address address) {
        User u = new User(nickname, firstName, lastName, mail, password, false, false, gender, birthday, phoneNumber, address);

        JpaUtil.createEntityManager();

        try {
            DAOUser.persist(u);
        } catch (Exception e) {
            u = null; // Registration has failed, return null to let the GUI know
        }

        return u;
    }

}