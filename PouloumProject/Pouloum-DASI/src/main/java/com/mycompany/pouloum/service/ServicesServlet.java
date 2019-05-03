/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.mycompany.pouloum.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Activity;
import com.mycompany.pouloum.model.Address;
import com.mycompany.pouloum.model.Badge;
import com.mycompany.pouloum.model.Event;
import com.mycompany.pouloum.model.Pouloumer;
import com.mycompany.pouloum.util.CRE;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.util.DateUtil;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.DBException;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Martin
 */
@WebServlet(name = "ServicesServlet", urlPatterns = {"/ServicesServlet"})
public class ServicesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.destroy();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(JsonServletHelper.ENCODING_UTF8);

        String sma = null;

        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            sma = pathInfo.substring(1);
        }

        String smaParameter = request.getParameter("SMA");
        if (smaParameter != null) {
            sma = smaParameter;
        }

        Gson g = new Gson();
        JsonObject container = new JsonObject();
        boolean serviceCalled = true;

        //////////
        ////login
        //////////
        try {
            //////////
            ////login
            //////////
            if ("login".equals(sma)) {
                String mail = request.getParameter("mail");
                String nickName = request.getParameter("nickname");
                String password = request.getParameter("password");

                Pouloumer p;
                if (mail != null) {
                    p = ServicesPouloumer.loginWithMail(mail, password);
                } else {
                    p = ServicesPouloumer.loginWithNickname(nickName, password);
                }
                if (p != null) {
                    container.add("Pouloumer", g.toJsonTree(p, Pouloumer.class));
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "There is no match for these identifiants.");
                }
            } //////////
            ////signUp
            //////////
            else if ("signUp".equals(sma)) {
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String nickName = request.getParameter("nickname");
                String mail = request.getParameter("mail");
                String password = request.getParameter("password");
                char gender = request.getParameter("gender").charAt(0);
                Date birthDate = DateUtil.toDate(request.getParameter("birthDate"));
                String phoneNumber = request.getParameter("phoneNumber");

                String addressNumber = request.getParameter("addressNumber");
                String addressStreet = request.getParameter("addressStreet");
                String addressPostalCode = request.getParameter("addressPostalCode");
                String addressCity = request.getParameter("addressCity");
                String addressCountry = request.getParameter("addressCountry");

                try {
                    ServicesAddress.createAddress(addressNumber, addressStreet, addressPostalCode, addressCity, addressCountry);
                    CRE result = ServicesPouloumer.signUp(lastName, firstName, nickName, mail, password, false, false, gender, birthDate, phoneNumber, null);
                    //FIXME make use of result
                    container.addProperty("result", "OK");
                } catch (Exception ex) {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to persist the new user");
                }
            } //////////
            ////consult home page
            //////////
            else if ("getUserEvents".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                if (p != null) {
                    JsonArray array = new JsonArray();
                    for (Event e : p.getEvents()) {
                        if (!(e.isCancelled() || e.isFinished())) {
                            array.add(e.toJson());
                        }
                    }
                    container.add("events", array);
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "invalid id");
                }
            } else if ("getUserBadges".equals(sma)) {
                //TODO when badges are implemented.
            } ///////////
            ////Consult profile
            ///////////
            else if ("getUserEventsHistory".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                if (p != null) {
                    JsonArray array = new JsonArray();
                    for (Event e : p.getEvents()) {
                        if (e.isFinished()) {
                            array.add(e.toJson());
                        }
                    }
                    container.add("eventsHistory", array);
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "invalid id");
                }
            } else if ("getUserFriends".equals(sma)) {

            } else if ("getUserBlacklist".equals(sma)) {

            } else if ("addInterestsToUser".equals(sma)) {

                long idUser = Long.parseLong(request.getParameter("idUser"));
                // TODO : récupérer les ids d'activity
                List<Long> idActivities = null;
                /* = request.getParameter("idActivities"); */
                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                List<Activity> interests = new ArrayList<>();
                for (Long idAct : idActivities) {
                    interests.add(ServicesActivity.getActivityById(idAct));
                }

                CRE result;
                if (p != null) {
                    result = ServicesPouloumer.addInterests(p, interests);
                } else {
                    result = CRE.CRE_ERR_INTEREST;
                }

                if (result == CRE.CRE_OK) {
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to process the transaction");
                }
            } else if ("removeInterestFromoUser".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));
                Long idActivity = Long.parseLong(request.getParameter("idActivity"));
                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Activity a = ServicesActivity.getActivityById(idActivity);

                CRE result;
                if (p != null && a != null) {
                    result = ServicesPouloumer.removeInterest(p, a);
                } else {
                    result = CRE.CRE_ERR_INTEREST;
                }

                if (result == CRE.CRE_OK) {
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to process the transaction");
                }
            } else if ("getUserInterests".equals(sma)) {

            } else if ("getUserDetails".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                if (p != null) {
                    container.add("pouloumer", p.toJson());
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "invalid id");
                }

            } else if ("acceptFriend".equals(sma)) {

            } else if ("removeFriend".equals(sma)) {

            } else if ("removeFromBlackList".equals(sma)) {

            } ////////////
            /////Consult someone else profile
            ////////////
            else if ("addToBlacklist".equals(sma)) {

            } else if ("sendFriendRequest".equals(sma)) {

            } else if ("reportAbusiveBehaviour".equals(sma)) {

            } /////////////
            /////Search for an event
            /////////////
            else if ("simpleSearchForUser".equals(sma)) {

            } else if ("joinEvent".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                CRE result = ServicesPouloumer.joinEvent(p, e);

                if (result == CRE.CRE_OK) {
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to process the transaction");
                }
            } else if ("leaveEvent".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                CRE eventResult = ServicesEvent.removeParticipant(p, e);

                if (eventResult == CRE.CRE_OK) {
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to process the transaction");
                }
            } //////////////
            /////Set up an event
            //////////////
            else if ("createEvent".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));
                //long idActivity = Long.parseLong(request.getParameter("idActivity"));
                //long idAddress = Long.parseLong(request.getParameter("idAddress"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Date startDate = DateUtil.toDate(request.getParameter("date"));
                int duration = Integer.parseInt(request.getParameter("duration"));
                int playerMin = Integer.parseInt(request.getParameter("playerMin"));
                int playerMax = Integer.parseInt(request.getParameter("playerMax"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                //Activity activity = ServicesActivity.getActivityById(idActivity);
                //Address address = ServicesAddress.getAddressById(idAddress);

                ArrayList<Pouloumer> participants = new ArrayList<Pouloumer>();
                participants.add(p);

                ServicesEvent.createEvent(name, description, startDate, duration, null, null, p, playerMin, playerMax, participants);
            } else if ("updateEvent".equals(sma)) {

            } else if ("cancelEvent".equals(sma)) {
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Event event = ServicesEvent.getEventById(idEvent);

                CRE result = ServicesEvent.cancelEvent(event);

                switch (result) {
                    case CRE_OK:
                        container.addProperty("result", "OK");
                        break;
                    case CRE_ERR_EVENT:
                        container.addProperty("result", "KO");
                        container.addProperty("message", "Event does not exist");
                        break;
                    case CRE_EXC_BD:
                        container.addProperty("result", "KO");
                        container.addProperty("message", "Error when trying to process the transaction");
                        break;
                }

            } else if ("getOrganizedEvents".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));

                List<Event> organizedEvents = ServicesEvent.getOrganizedEvents(idUser);

                if (organizedEvents != null) {
                    JsonArray organizedEventsArray = new JsonArray();

                    for (Event e : organizedEvents) {
                        organizedEventsArray.add(e.toJson());
                    }

                    container.add("organizedEvents", organizedEventsArray);
                    container.addProperty("result", "OK");
                } else { // Return is null
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to read the database");
                }
            } ///////////////
            /////Consult finished event
            ///////////////
            else if ("addCommentToEvent".equals(sma)) {

            } ///////////////
            /////Consult an activity
            ///////////////
            else if ("findAllActivities".equals(sma)) {
                JsonArray array = new JsonArray();
                
                List<Activity> activities = ServicesActivity.findAllActivities();
                                
                for (Activity a : activities)
                {
                    array.add(a.toJson());
                }
                
                if (!activities.isEmpty())
                {
                    container.add("activities", array);
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to read the database");
                }
            } else if ("getActivityDetails".equals(sma)) {
                long idActivity = Long.parseLong(request.getParameter("idActivity"));
                
                Activity a = ServicesActivity.getActivityById(idActivity);
                
                if (a != null)
                {
                    container.add("Activity", a.toJson());
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error while trying to access the database");
                }
            } /////////////////
            //////Consult an event
            /////////////////
            else if ("getEventDetails".equals(sma)) {
                long idEvent = Long.parseLong(request.getParameter("idEvent"));
                
                Event e = ServicesEvent.getEventById(idEvent);
                
                if (e != null) {
                    container.add("event", e.toJson());
                    container.addProperty("result", "OK");
                } else {
                    container.addProperty("result", "KO");
                    container.addProperty("message", "Error when trying to read the database");                    
                }
                
                
                JsonObject obj = new JsonObject();
            } /////////////////
            //////Update profile
            /////////////////
            else if ("updateUserDetails".equals(sma)) {

            } else {
                serviceCalled = false;
            }
        } catch (ServiceException ex) {
            container.addProperty("error", ex.getMessage());
            // } catch (ParseException ex) {
            //     Logger.getLogger(ServicesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServicesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (serviceCalled) {
            JsonServletHelper.printJsonOutput(response, container);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service SMA '" + sma + "' not found");
        }
    }
}
