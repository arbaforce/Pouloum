/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.event;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;

/**
 *
 * @author Martin
 */
public class PouloumSOM {
  
    protected DBConnection dBConnection;
    protected JsonObject container;

    public PouloumSOM(JsonObject container) {
        this.container = container;
    }
    
    public void release() {
        this.dBConnection.close();
    }
  
}