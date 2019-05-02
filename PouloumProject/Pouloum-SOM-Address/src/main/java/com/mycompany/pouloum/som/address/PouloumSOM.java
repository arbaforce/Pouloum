/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.address;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.dao.DAOAddress;
import com.mycompany.pouloum.dao.JpaUtil;


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
     * @param idAddress is the id of the Address to find.
     * @return Address, the matching address.
     * @throws Exception if there's an error trying to access the database.
     */
    public Address getAddressById(Long idAddress)
            throws Exception
    {
        JpaUtil.createEntityManager();

        Address a = DAOAddress.findById(idAddress);

        JpaUtil.closeEntityManager();

        return a;
    }
  
    /**
     * Try to login with a given (mail, password) pair.
     *
     * @param newAddress is the new address to create.
     * @return id, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public Long createAddress(Address newAddress)
            throws Exception
    {
        JpaUtil.createEntityManager();

        JpaUtil.openTransaction();

        try {
            DAOAddress.persist(newAddress);
            JpaUtil.commitTransaction();
        } catch (Exception ex) {
            JpaUtil.cancelTransaction();
        }
        
        JpaUtil.closeEntityManager();

        return newAddress.getId();
    }
    
    
}
