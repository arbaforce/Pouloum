package com.mycompany.pouloum.model;

import com.google.gson.JsonObject;
import java.io.Serializable;
import java.text.ParseException;
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
import com.mycompany.pouloum.util.DateUtil;
import javax.persistence.Column;



@Entity
public class Event implements Serializable  {
    
    // ATTRIBUTES
    class Commentary 
    {
        public Commentary(String description, Date date, Long idUser) {
            this.date = date;
            this.description = description;
            this.idUser = idUser;
        }
        public Date date; 
        public String description;
        public Long idUser;
    }
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
    @Column(name="datetime")
    protected Date start;
    protected int duration;
    
    // Coordinates
    @OneToOne
    protected Address location;
    @OneToOne
    protected Activity activity;
    
    // People
    @OneToOne
    protected Pouloumer organizer;
    protected int participants_min;
    protected int participants_max;
    @OneToMany
    protected List<Pouloumer> participants;
    
    // Grades
    protected List<Commentary> comments;
    // protected double grade_average;
    // map<Pouloumer,int> participants_gradings
    // map<Pouloumer,list<String>> participants_tonotify
    
    
    // CONSTRUCTORS
    
    public Event( ) { }
    
    public Event(String label, String description, boolean cancelled, Date start, int duration, Address location, Activity activity, Pouloumer organizer, int participants_min, int participants_max, List<Pouloumer> participants) {
        this.label = label;
        this.description = description;
        this.cancelled = cancelled;
        this.start = start;
        this.duration = duration;
        this.location = location;
        this.activity = activity;
        this.organizer = organizer;
        this.participants_min = participants_min;
        this.participants_max = participants_max;
        this.participants = participants;
    }
    
    public Event(String label, String description, boolean cancelled, String start, int duration, Address location, Activity activity, Pouloumer organizer, int participants_min, int participants_max, List<Pouloumer> participants)
        throws ParseException
    {
        this.label = label;
        this.description = description;
        this.cancelled = cancelled;
        this.setStart(start);
        this.duration = duration;
        this.location = location;
        this.activity = activity;
        this.organizer = organizer;
        this.participants_min = participants_min;
        this.participants_max = participants_max;
        this.participants = participants;
    }
    
    
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

    public boolean getCancelled() {
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
    
    public void setStart( String start )
        throws ParseException
    {
        this.start = DateUtil.toDate(start);
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

    public Pouloumer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Pouloumer organizer) {
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

    /*
    public double getGrade_average() {
        return grade_average;
    }

    public void setGrade_average(double grade_average) {
        this.grade_average = grade_average;
    }
    */
    
    public List<Pouloumer> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<Pouloumer> participants) {
        this.participants = participants;
    }
    
    public void addParticipant(Pouloumer participant) {
        this.participants.add(participant);
    }
    
    public void removeParticipant(Pouloumer participant) {
        for (Pouloumer p : participants){
            if (p.getId()==participant.getId()){
                participants.remove(p);
                break;
            }
        }
    }
    
    public void addCommentary(String description, Date date, Long idPouloumer) {
        this.comments.add(new Commentary(description, date, idPouloumer));
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
    
    public JsonObject toJson (){
        JsonObject obj = new JsonObject();
        
        obj.addProperty("label", label);
        
        return obj;
    }
}
