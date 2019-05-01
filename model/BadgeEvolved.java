package model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class BadgeEvolved implements Serializable {
    
    // ATTRIBUTES
    
    protected int strength;
    protected Badge type;
    
    
    // CONSTRUCTORS
    
    public BadgeEvolved ( ) {
        this.strength = 0;
    }
    
    public BadgeEvolved( int strength, Badge type ) {
        this.strength = strength;
        this.type = type;
    }
    
    
    // GETTERS AND SETTERS

    public int getStrength( ) {
        return strength;
    }
    public void setStrength( int strength ) {
        this.strength = strength;
    }
    
    public Badge getType( ) {
        return type;
    }
    public void setType( Badge type ) {
        this.type = type;
    }
    
}

