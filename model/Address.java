package model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Address implements Serializable {
    
    // ATTRIBUTES
    
    private String number;
    private String street;
    private String postal_code;
    private String city;
    private String country;
    
    
    // CONSTRUCTORS
    
    public Address( ) { }
    
    public Address( String number, String street, String postal_code, String city, String country )
    {
        this.number = number;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }
    
    
    // SETTERS AND GETTERS
    
    // ...
    
    
    @Override
    public String toString() {
        return "" + number + " " + street + " " + postal_code + " " + city + " " + country + "";
    }
    
}
