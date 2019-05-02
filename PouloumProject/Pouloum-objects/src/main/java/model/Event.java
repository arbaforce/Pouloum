package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import util.DateUtil;


@Entity
public class Event implements Serializable  {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    // Description
    protected String label;
    protected String description;
    protected boolean cancelled;
    
    // Time
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date start;
    protected int duration;
    
    // Coordinates
    @OneToOne
    protected Address location;
    @OneToOne
    protected Activity activity;
    
    // People
    @OneToOne
    protected User organizer;
    protected int participants_min;
    protected int participants_max;
    @OneToMany
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public int getParticipants_min() {
        return participants_min;
    }

    public void setParticipants_min(int participants_min) {
        this.participants_min = participants_min;
    }

    public int getParticipants_max() {
        return participants_max;
    }

    public void setParticipants_max(int participants_max) {
        this.participants_max = participants_max;
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

    public double getGrade_average() {
        return grade_average;
    }

    public void setGrade_average(double grade_average) {
        this.grade_average = grade_average;
    }
    
    public List<User> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    
    
    // ...
    
    public int getParticipants_num() {
        return participants.size();
    }
    
    public int getStartHour() {
        SimpleDateFormat h = new SimpleDateFormat("HH:mm");
        String heureMinuteEvent = h.format(this.start);
        String[] infosdate = (heureMinuteEvent.split(":"));
        int hour = Integer.parseInt(infosdate[0]);
        return hour;
    }
    
    public void setStart( int year, int month, int day, int hour, int minutes ) {
        Date date = DateUtil.DateNew(year, month, day, hour, minutes, 0);
        setStart(date);
    }
    
    public boolean isStarted() {
        return this.start.before(DateUtil.DateNow());
    }
    
    public Date getEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.start);
        cal.add(Calendar.MINUTE, this.duration);
        Date datetime = cal.getTime();
        return datetime;
    }
    
    public boolean isFinished() {
        return getEnd().before(DateUtil.DateNow());
    }
    
}
