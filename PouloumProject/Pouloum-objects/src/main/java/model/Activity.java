package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Activity implements Serializable  {
    
    // ATTRIBUTES
    
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    // Hierarchy
    @OneToOne
    protected Activity parent;
    @OneToMany
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
    
    public Long getId( ) {
        return id;
    }
    
    public void setId( Long id ) {
        this.id = id;
    }

    public Activity getParent() {
        return parent;
    }

    public void setParent(Activity parent) {
        this.parent = parent;
    }

    public List<Activity> getChildren() {
        return children;
    }

    public void setChildren(List<Activity> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public int getDefault_participants_min() {
        return default_participants_min;
    }
    
    public void setDefault_participants_min(int default_participants_min) {
        this.default_participants_min = default_participants_min;
    }

    public int getDefault_participants_max() {
        return default_participants_max;
    }
    
    public void setDefault_participants_max(int default_participants_max) {
        this.default_participants_max = default_participants_max;
    }
    
}
