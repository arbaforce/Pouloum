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
     * Get an address, given its id.
     *
     * @param idAddress is the id of the Address to find.
     * @throws Exception if there's an error trying to access the database,
     * or if there is no address with the given id.
     * @return Address, the matching address.
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
     * Create an address.
     *
     * @param number is the address number.
     * @param street is the address street.
     * @param postal_code is the address postal code.
     * @param city is the address city.
     * @param country is the address country.
     * @return Address, the created address.
     * @throws Exception if there's an error trying to access the database.
     */
    public static Address createAddress(String number, String street, String postal_code, String city, String country)
            throws Exception
    {
        Address newAddress = new Address(number, street, postal_code, city, country);
        
        JpaUtil.createEntityManager();
        
        try {
            JpaUtil.openTransaction();
            
            try {
                DAOAddress.persist(newAddress);
                
                JpaUtil.commitTransaction();
                return newAddress;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
}
