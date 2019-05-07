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

    /**
     * Try to login using e-mail address. The result into a JSON container.
     *
     * @param mail is the mail of the user.
     * @param password is the password of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
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
                this.container.add("userID", smaResultContainer.get("pouloumer"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("loginByMail", ex);
        }
    }

    /**
     * Try to login using nickname. The result is stored into a JSON container.
     *
     * @param nickname is the nickname of the user.
     * @param password is the password of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
    public void loginByNickname(String nickname, String password) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "login"),
                    new JsonHttpClient.Parameter("nickname", nickname),
                    new JsonHttpClient.Parameter("password", password)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "loginByNickname");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("userID", smaResultContainer.get("pouloumer"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("loginByNickname", ex);
        }
    }

    /**
     * Try to register a new user. The result is stored into a JSON container.
     *
     * @param lastName is the last name of the user.
     * @param firstName is the first name of the user.
     * @param nickname is the nickname of the user.
     * @param mail is the email address of the user.
     * @param password is the password of the user.
     * @param isModerator states whether the new user should be a moderator.
     * @param isAdmin states whether the new user should be an admin.
     * @param gender is the gender of the user.
     * @param birthDate is the birth date of the user.
     * @param phoneNumber is the phone number of the user.
     * @param addressNumber is the address' number of the user.
     * @param addressStreet is the address' street of the user.
     * @param addressPostalCode is the address' postal code of the user.
     * @param addressCity is the city of the user.
     * @param addressCountry is the country of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
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

    /**
     * Get the events (either past or upcoming) for an user into a JSON container.
     *
     * @param id is the id of the user.
     * @param history states which events (past or upcoming ones) should be
     * retrieved.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
    public void getUserEvents(String id, String history) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getUserUpcomingEvents"),
                    new JsonHttpClient.Parameter("idUser", id),
                    new JsonHttpClient.Parameter("history", history)
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

    /**
     * Get the badges of a given user into a JSON container.
     *
     * @param id is the id of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
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

    /**
     * Get all the stored information about an user into a JSON container.
     *
     * @param id is the of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
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

    /**
     * Update a given user's informations. The result is stored into a JSON
     * container.
     *
     * @param id is the id of the user.
     * @param lastName is the last name of the user.
     * @param firstName is the first name of the user.
     * @param gender is the gender of the user.
     * @param nickname is the nickname of the user.
     * @param password is the password of the user.
     * @param birthDate is the birth date of the user.
     * @param mail is the e-mail address of the user.
     * @param phoneNumber is the phone number of the user.
     * @param country is the country of the user.
     * @param city is the city of the user.
     * @param postalCode is the address' postal code of the user.
     * @param street is the address' street of the user.
     * @param streetNumber is the address' number of the user.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
    public void updateUserDetails(String id, String lastName, String firstName, String gender, String nickname, String password, String birthDate, String mail, String phoneNumber, String country, String city, String postalCode, String street, String streetNumber) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "updateUserDetails"),
                    new JsonHttpClient.Parameter("idUser", id),
                    new JsonHttpClient.Parameter("lastName", lastName),
                    new JsonHttpClient.Parameter("firstName", firstName),
                    new JsonHttpClient.Parameter("nickname", nickname),
                    new JsonHttpClient.Parameter("mail", mail),
                    new JsonHttpClient.Parameter("password", password),
                    new JsonHttpClient.Parameter("birthDate", birthDate),
                    new JsonHttpClient.Parameter("phoneNumber", phoneNumber),
                    new JsonHttpClient.Parameter("addressCountry", country),
                    new JsonHttpClient.Parameter("addressCity", city),
                    new JsonHttpClient.Parameter("addressNumber", streetNumber),
                    new JsonHttpClient.Parameter("addressStreet", street),
                    new JsonHttpClient.Parameter("addressPostalCode", postalCode)
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

    public void addInterest(String idUser, String interest) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "addInterestsToUser"),
                    new JsonHttpClient.Parameter("idUser", idUser),
                    new JsonHttpClient.Parameter("idActivities", idUser)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "addInterest");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getUserDetails", ex);
        }
    }

    /**
     * Get all available information about a given event into a JSON container.
     *
     * @param id is the id of the event.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
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

    /**
     * Get all available information about a given activity into a JSON
     * container.
     *
     * @param id is the id of the activity.
     * @throws ServiceException if something goes wrong when calling the
     * service.
     */
    public void getActivityDetails(String id) throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "getActivityDetails"),
                    new JsonHttpClient.Parameter("activityID", id)
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "getActivityDetails");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("activity", smaResultContainer.get("activity"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }

        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("getActivityDetails", ex);
        }
    }
    
    public void findAllActivities() throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "findAllActivities")
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "findAllActivities");
            }

            String result = smaResultContainer.get("result").getAsString();

            if ("OK".equals(result)) {
                this.container.addProperty("result", true);
                this.container.add("activities", smaResultContainer.get("activities"));
            } else {
                this.container.addProperty("result", false);
                this.container.addProperty("errorMessage", "ERROR : " + smaResultContainer.get("message").getAsString());
            }
        } catch (IOException ex) {
            throw JsonServletHelper.ActionExecutionException("findAllActivities", ex);
        }
    }
    
    public void findAllEvents() throws ServiceException {
        try {
            JsonObject smaResultContainer = this.jsonHttpClient.post(
                    this.smaUrl,
                    new JsonHttpClient.Parameter("SMA", "findAllEvents")
            );

            if (!JsonHttpClient.checkJsonObject(smaResultContainer)) {
                throw JsonServletHelper.ServiceMetierCallException(this.smaUrl, "findAllEvents");
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
            throw JsonServletHelper.ActionExecutionException("findAllEvents", ex);
        }
    }
}
