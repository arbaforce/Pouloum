package com.mycompany.pouloum.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Activity implements Serializable {

    // ATTRIBUTES
    // Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    // Hierarchy
    @OneToOne(fetch = FetchType.LAZY)
    protected Activity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
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
    public Activity() {
    }

    public Activity(Activity parent, String name, String description, List<Badge> badges, String rules, List<String> resources, int default_participants_min, int default_participants_max) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.badges = badges;
        this.rules = rules;
        this.resources = resources;
        this.default_participants_min = default_participants_min;
        this.default_participants_max = default_participants_max;
    }

    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    
    public void addChild(Activity child){
        children.add(child);
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

    public JsonObject toJson(boolean includeHierarchy) {
        JsonObject obj = new JsonObject();

        obj.addProperty("id", id);
        
        if (includeHierarchy) {
            if (parent!=null) {
                obj.addProperty("parent", parent.getName());
            } else {
                obj.addProperty("parent", "");
            }
            
            JsonArray childrenArray = new JsonArray();
            for (Activity a : children) {
                childrenArray.add(a.toJson(includeHierarchy));
            }
            obj.add("children", childrenArray);
        }
        
        obj.addProperty("name", name);
        obj.addProperty("description", description);
        
        JsonArray badgesArray = new JsonArray();
        
        for (Badge b : badges) {
            badgesArray.add(b.toJson());
        }
        
        obj.add("badges", badgesArray);
        obj.addProperty("rules", rules);
        
        JsonArray resourcesArray = new JsonArray();
        
        for (String s : resources) {
            resourcesArray.add(s);
        }
        
        obj.add("resources", resourcesArray);
        
        obj.addProperty("default_participants_min", default_participants_min);
        obj.addProperty("default_participants_max", default_participants_max);
        
        return obj;
    }
}
