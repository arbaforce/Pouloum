package model;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import static util.GeoTest.getLatLng;


@Entity
public class Address implements Serializable {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Address components
    private String number;
    private String street;
    private String postal_code;
    private String city;
    private String country;
    
    
    // CONSTRUCTORS
    
    public Address( ) { }
    
    public Address(String number, String street, String postal_code, String city, String country)
    {
        this.number = number;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }
    
    
    // SETTERS AND GETTERS
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
    // ...
    
    @Override
    public String toString() {
        return "" + number + " " + street + " " + postal_code + " " + city + " " + country + "";
    }
    
    public LatLng getCoords() {
        return getLatLng(this.toString());
    }
    
}
