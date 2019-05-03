/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.ihm.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.JsonHttpClient;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Martin
 */
public class AjaxAction {
  
  protected String smaUrl;
    protected JsonObject container;

    protected JsonHttpClient jsonHttpClient;

    protected static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
    protected static final SimpleDateFormat FULL_DATETIME_FORMAT = new SimpleDateFormat("dd/MM/YYYY @ HH'h'mm");

    public AjaxAction(String smaUrl, JsonObject container) {
        this.smaUrl = smaUrl;
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
    
    public void loginByMail (String mail, String password) throws ServiceException{
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "login"),
                    new JsonHttpClient.Parameter("mail", mail),
                    new JsonHttpClient.Parameter("password", password)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "loginByMail");
            }
            
            String result = smaResultContainer.get("result").getAsString();
            
            if("OK".equals(result)){
                this.container.addProperty("result", true);
                this.container.add("user", smaResultContainer.get("Pouloumer"));
            }
            else{
                this.container.addProperty("error", "Les identifiants n'ont pas été trouvés");
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("rechercherClientParNumero", ex);
        }
    }
  
    public void loginByNickName (String nickName, String password) throws ServiceException{
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "login"),
                    new JsonHttpClient.Parameter("nickname", nickName),
                    new JsonHttpClient.Parameter("password", password)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "loginByNickName");
            }
            
            String result = smaResultContainer.get("result").getAsString();
            
            if("OK".equals(result)){
                this.container.addProperty("result", true);
                this.container.add("user", smaResultContainer.get("Pouloumer"));
            }
            else{
                this.container.addProperty("error", "Les identifiants n'ont pas été trouvés");
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("loginByNickName", ex);
        }
    }
}
