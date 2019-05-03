package com.mycompany.pouloum.model;

import com.google.gson.JsonObject;

public enum Badge {
    FIRE,
    WATER,
    GRASS;

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        
        switch(this) {
            case FIRE:
                obj.addProperty("type", "fire");
                break;
            case WATER:
                obj.addProperty("type", "water");
                break;
            case GRASS:
                obj.addProperty("type", "grass");
                break;
        }
        
        return obj;        
    }
}
