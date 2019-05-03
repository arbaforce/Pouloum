/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOAddress;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Address;

/**
 *
 * @author Martin
 */
public class ServicesAddress {
    
    /**
     * Try to login with a given (mail, password) pair.
     *
     * @param idAddress is the id of the Address to find.
     * @return Address, the matching address.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Address getAddressById(Long idAddress)
            throws Exception
    {
        JpaUtil.createEntityManager();

        try {
            Address a = DAOAddress.findById(idAddress);
            return a;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
  
    /**
     * Try to login with a given (mail, password) pair.
     *
     * @param number is the address number.
     * @param street is the address street.
     * @param postal_code is the address postal code.
     * @param city is the address city.
     * @param country is the address country.
     * @return id, the user matching the credentials or null if they are
     * incorrect.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Long createAddress(String number, String street, String postal_code, String city, String country)
            throws Exception
    {
        Address newAddress = new Address(number, street, postal_code, city, country);
        
        JpaUtil.createEntityManager();
        
        try {
            JpaUtil.openTransaction();
            
            try {
                DAOAddress.persist(newAddress);
                
                JpaUtil.commitTransaction();
                return newAddress.getId();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
}
