/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.sma;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.JsonHttpClient;
import java.io.IOException;

/**
 *
 * @author Martin
 */
public class PouloumSM {
  
    protected final String somUserUrl;
    protected final String somBadgeUrl;
    protected final String somAddressUrl;
    protected final String somActivityUrl;
    protected final String somEventUrl;
    protected final JsonObject container;

    protected JsonHttpClient jsonHttpClient;

    public PouloumSM(String somUserUrl, String somBadgeUrl, String somAddressUrl, String somActivityUrl, String somEventUrl, JsonObject container) {
        this.somUserUrl = somUserUrl;
        this.somBadgeUrl = somBadgeUrl;
        this.somAddressUrl = somAddressUrl;
        this.somActivityUrl = somActivityUrl;
        this.somEventUrl = somEventUrl;
        this.container = container;

        this.jsonHttpClient = new JsonHttpClient();
    }

    public void release() {
        try {
            this.jsonHttpClient.close();
        } catch (IOException ex) {
            // Ignorer
        }
    }
  
}