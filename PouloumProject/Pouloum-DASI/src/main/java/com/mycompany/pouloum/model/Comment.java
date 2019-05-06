package com.mycompany.pouloum.model;

import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import com.mycompany.pouloum.util.DateUtil;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Comment implements Serializable {
    
    // ATTRIBUTES
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date postTime;
    
    protected String text;
    
    @ManyToOne
    @JoinColumn(name="author")
    protected Pouloumer author;
    
    @ManyToOne
    protected Event event;
    
    // CONSTRUCTORS
    
    public Comment ( ) {
    }
    
    public Comment(String description, Date date, Pouloumer user, Event event) {
        this.postTime = date;
        this.text = description;
        this.author = user;
        this.event = event;
    }

        
    // SETTERS AND GETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Pouloumer getAuthor() {
        return author;
    }

    public void setAuthor(Pouloumer author) {
        this.author = author;
    }
    
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    
    // ...
    
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("idUser", author.getId());
        obj.addProperty("idEvent", event.getId());
        obj.addProperty("date", DateUtil.toString(postTime));
        obj.addProperty("description", text);
        return obj;
    }
    
}
