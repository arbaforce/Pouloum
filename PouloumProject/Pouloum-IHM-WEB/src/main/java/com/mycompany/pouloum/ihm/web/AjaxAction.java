package com.mycompany.pouloum.ihm.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.JsonHttpClient;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void loginByMail(String mail, String password) throws ServiceException {
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

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("userID", smaResultContainer.get("Pouloumer"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("rechercherClientParNumero", ex);
        }
    }

    public void loginByNickName(String nickName, String password) throws ServiceException {
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

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("userID", smaResultContainer.get("Pouloumer"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("loginByNickName", ex);
        }
    }

    public void signUp(String lastName, String firstName, String nickname,
            String mail, String password, Boolean isModerator, Boolean isAdmin,
            Character gender, String birthDate, String phoneNumber, String addressNumber,
            String addressStreet, String addressPostalCode, String addressCity,
            String addressCountry) throws ServiceException {

        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "signUp"),
                    new JsonHttpClient.Parameter("nickname", nickname),
                    new JsonHttpClient.Parameter("mail", mail),
                    new JsonHttpClient.Parameter("password", password),
                    new JsonHttpClient.Parameter("lastName", lastName),
                    new JsonHttpClient.Parameter("firstName", firstName),
                    new JsonHttpClient.Parameter("isModerator", isModerator.toString()),
                    new JsonHttpClient.Parameter("isAdmin", isAdmin.toString()),
                    new JsonHttpClient.Parameter("gender", gender.toString()),
                    new JsonHttpClient.Parameter("birthDate", birthDate),
                    new JsonHttpClient.Parameter("phoneNumber", phoneNumber),
                    new JsonHttpClient.Parameter("addressNumber", addressNumber),
                    new JsonHttpClient.Parameter("addressStreet", addressStreet),
                    new JsonHttpClient.Parameter("addressPostalCode", addressPostalCode),
                    new JsonHttpClient.Parameter("addressCity", addressCity),
                    new JsonHttpClient.Parameter("addressCountry", addressCountry)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "signUp");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);

                // TODO get other fields ?
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("signUp", ex);
        }

    }

    public void getUserEvents(String id) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getUserEvents"),
                    new JsonHttpClient.Parameter("idUser", id)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "getUserEvents");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("events", smaResultContainer.get("events"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getUserEvents", ex);
        }
    }

    public void getUserBadges(String id) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getUserBadges"),
                    new JsonHttpClient.Parameter("idUser", id)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "getUserBadges");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("badges", smaResultContainer.get("badges"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getUserBadges", ex);
        }
    }

    public void getUserDetails(String id) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getUserDetails"),
                    new JsonHttpClient.Parameter("idUser", id)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "getUserDetails");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("userDetails", smaResultContainer.get("pouloumer"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getUserDetails", ex);
        }
    }

    public void updateUserDetails(String id, String surname, String name, String gender, String pseudo, String password, String birthDate, String mail, String phoneNumber, String country, String city, String postal_number, String street, String street_number) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "updateUserDetails"),
                    new JsonHttpClient.Parameter("idUser", id),
                    new JsonHttpClient.Parameter("lastName", surname),
                    new JsonHttpClient.Parameter("firstName", name),
                    new JsonHttpClient.Parameter("nickname", pseudo),
                    new JsonHttpClient.Parameter("mail", mail),
                    new JsonHttpClient.Parameter("password", password),
                    new JsonHttpClient.Parameter("birthDate", birthDate),
                    new JsonHttpClient.Parameter("phoneNumber", phoneNumber),
                    new JsonHttpClient.Parameter("addressCountry", country),
                    new JsonHttpClient.Parameter("addressCity", city),
                    new JsonHttpClient.Parameter("addressNumber", street_number),
                    new JsonHttpClient.Parameter("addressStreet", street),
                    new JsonHttpClient.Parameter("addressPostalCode", postal_number)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "updateUserDetails");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("updateUserDetails", ex);
        }
    }

    public void getEventDetails(String id) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getEventDetails"),
                    new JsonHttpClient.Parameter("eventID", id)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "getEventDetails");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("event", smaResultContainer.get("event"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getEventDetails", ex);
        }
    }
}
