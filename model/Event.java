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
public abstract class Event implements Serializable  {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    // Description
    protected String name;
    protected String description;
    protected bool is_cancelled;
    
    // Time
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date start;
    protected int duration;
    
    // Coordinates
    protected Address location;
    protected Activity activity;
    
    // People
    protected User organizer;
    protected int participants_min;
    protected int participants_max;
    @OneToMany(mappedBy="event")
    protected List<User> participants;
    
    protected String email;
    protected String password;
    
    // Grades
    protected double grade_average;
    // map<User,int> participants_gradings
    // map<User,list<String>> participants_tonotify
    
    
    // CONSTRUCTORS
    
    public Event( ) { }
    
    /*
    public Event( ... )
        throws ParseException
    {
        this.setStart(start);
    }
    */
    
    
    // SETTERS AND GETTERS

    public List<User> getParticipants() {
        return participants;
    }
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    
}
