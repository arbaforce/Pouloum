package com.mycompany.pouloum.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BadgeEvolved implements Serializable {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Base badge
    protected Badge type;
    
    // Characteristics
    protected int strength;
    
    
    // CONSTRUCTORS
    
    public BadgeEvolved ( ) {
        this.strength = 0;
    }
    
    public BadgeEvolved(int strength, Badge type) {
        this.strength = strength;
        this.type = type;
    }
    
    
    // GETTERS AND SETTERS
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Badge getType( ) {
        return type;
    }
    
    public void setType( Badge type ) {
        this.type = type;
    }
    
    public int getStrength( ) {
        return strength;
    }
    
    public void setStrength( int strength ) {
        this.strength = strength;
    }
    
}

