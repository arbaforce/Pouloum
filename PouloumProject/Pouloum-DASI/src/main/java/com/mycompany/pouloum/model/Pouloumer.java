package com.mycompany.pouloum.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import com.mycompany.pouloum.util.DateUtil;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
public class Pouloumer implements Serializable {

    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    // Identity 
    @Column(unique = true)
    protected String nickname;
    protected String first_name;
    protected String last_name;

    // Account info
    @Column(unique = true)
    protected String email;
    protected String password;
    protected boolean moderator;
    protected boolean administrator;

    // Personal information
    protected char gender;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date birth_date;
    protected String phone_number;

    // Coordinates
    @OneToOne
    protected Address address;

    // Links
    @OneToMany(fetch=FetchType.LAZY, mappedBy="organizer")
    protected List<Event> organizedEvents;
    @ManyToMany(fetch=FetchType.LAZY, mappedBy="participants")
    protected List<Event> events;

    @OneToMany
    protected List<Activity> interests;

    // To implement later
    // - badges
    // - rank
    // - interests
    // - friends
    // - blacklist
    
    
    // CONSTRUCTORS
    
    public Pouloumer() {
    }

    public Pouloumer(String nickname, String first_name, String last_name, String email, String password, boolean moderator, boolean administrator, char gender, Date birth_date, String phone_number, Address address) {
        this.nickname = nickname;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.moderator = moderator;
        this.administrator = administrator;
        this.gender = gender;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
        this.address = address;

        this.events = new ArrayList<>();
        this.interests = new ArrayList<>();
    }

    public Pouloumer(String nickname, String first_name, String last_name, String email, String password, boolean moderator, boolean administrator, char gender, String birth_date, String phone_number, Address address)
            throws ParseException {
        this.nickname = nickname;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.moderator = moderator;
        this.administrator = administrator;
        this.gender = gender;
        this.setBirth_date(birth_date);
        this.phone_number = phone_number;
        this.address = address;

        this.events = new ArrayList<>();
        this.interests = new ArrayList<>();
    }

    // SETTERS AND GETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setBirth_date(String birth_date)
            throws ParseException {
        this.birth_date = DateUtil.toDate(birth_date);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Activity> getInterests() {
        return interests;
    }

    public void setInterests(List<Activity> interests) {
        this.interests = interests;
    }
    
    
    // ...
    
    public int getAge() {
        Date now = DateUtil.DateNow();
        long ageindays = DateUtil.DateDiff(birth_date, now, TimeUnit.DAYS);
        return (int) (ageindays / 365);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        for (Event event : events) {
            if (event.getId() == e.getId()) {
                events.remove(event);
                break;
            }
        }
    }
    
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        
        obj.addProperty("id", id); //TODO consider removing this
        obj.addProperty("nickname", nickname);
        obj.addProperty("last_name", last_name);
        obj.addProperty("first_name", first_name);
        obj.addProperty("email", email);
        obj.addProperty("password", password);
        obj.addProperty("moderator", moderator);
        obj.addProperty("administrator", administrator);
        obj.addProperty("gender", gender);
        obj.addProperty("birth_date", DateUtil.toString(birth_date));
        obj.addProperty("phone_number", phone_number);
        if (address!=null)
            obj.add("address", address.toJson());
        
        JsonArray eventsArray = new JsonArray();
        
        for (Event e : events) {
            eventsArray.add(e.toJson());
        }
        
        obj.add("events", eventsArray);
        JsonArray interestsArray = new JsonArray();
        
        for (Activity a : interests) {
            interestsArray.add(a.toJson());
        }
        
        return obj;
    }
}
