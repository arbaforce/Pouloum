package model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;


@Entity
public abstract class Activity implements Serializable  {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    // Hierarchy
    protected Activity parent;
    protected List<Activity> children;
    
    // Description
    protected String name;
    protected String description;
    protected List<Badge> badges;
    
    // Links
    protected String rules;
    protected List<String> resources;
    
    // Default data
    protected int default_participants_min;
    protected int default_participants_max;
    
    
    // CONSTRUCTORS
    
    public Activity( ) {}
    
    
    // GETTERS AND SETTERS
    
    // Id
    public Long getId( ) {
        return id;
    }
    public void setId( Long id ) {
        this.id = id;
    }
    
}
