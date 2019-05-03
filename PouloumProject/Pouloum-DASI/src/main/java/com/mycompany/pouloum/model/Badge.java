package com.mycompany.pouloum.model;

import com.google.gson.JsonObject;

public enum Badge {
    FIRE,
    WATER,
    GRASS;
    
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        
        obj.addProperty("type", this.toString());
        
        return obj;        
    }
}
