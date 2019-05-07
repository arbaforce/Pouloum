package com.mycompany.pouloum.model;

import com.google.gson.JsonObject;

public enum Badge {
    EARTH,
    FIRE,
    WATER,
    WIND;
    
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        
        obj.addProperty("type", this.toString());
        
        return obj;        
    }
}
