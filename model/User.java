package model;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;


@Entity
public abstract class User implements Serializable  {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    // Identity
    protected char gender;
    protected String nickname;
    protected String first_name;
    protected String family_name;
    protected String phone_number;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date birth_date;
    protected bool is_moderator;
    protected bool is_administrator;
    
    // Coordinates
    protected Address address;
    
    // Account
    protected String email;
    protected String password;
    
    // Links
    @OneToMany(mappedBy="user")
    private List<Event> events;
    
    // To implement later
    
    // - badges
    // - rank
    // - interests
    // - friends
    // - blacklist
    
    
    // CONSTRUCTORS
    
    public Personne( ) { }
    
    /*
    public Personne( ... ) 
        throws ParseException
    {
        this.setDateDeNaissance(dateDeNaissance);
    }
    */
    
    
    // SETTERS AND GETTERS

    public Date getBirth_date( ) {
        return birth_date;
    }
    
    public void setBirth_date( Date birth_date ) {
        this.birth_date = birth_date;
    }
    public void setBirth_date( String birth_date )
        throws ParseException
    {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        this.birth_date = sf.parse(birth_date);
    }
    
    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
}
