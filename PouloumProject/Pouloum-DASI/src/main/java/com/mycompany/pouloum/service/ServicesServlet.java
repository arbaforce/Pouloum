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
import static com.mycompany.pouloum.util.CRE.*;
import com.mycompany.pouloum.util.Common;
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
import java.util.Map;
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
        String resultErrorMessage = "";

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
                    container.addProperty("Pouloumer", p.getId());
                } else {
                    resultErrorMessage = "There is no match for these identifiants.";
                }
            } //////////
            ////signUp
            //////////
            else if ("signUp".equals(sma)) {
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String nickName = request.getParameter("nickname");
                String email = request.getParameter("mail");
                String password = request.getParameter("password");
                char gender = request.getParameter("gender").charAt(0);
                Date birthDate = DateUtil.toDate(request.getParameter("birthDate"));
                String phoneNumber = request.getParameter("phoneNumber");

                String addressNumber = request.getParameter("addressNumber");
                String addressStreet = request.getParameter("addressStreet");
                String addressPostalCode = request.getParameter("addressPostalCode");
                String addressCity = request.getParameter("addressCity");
                String addressCountry = request.getParameter("addressCountry");

                //Address address = ServicesAddress.createAddress(addressNumber, addressStreet, addressPostalCode, addressCity, addressCountry);
                Address address = new Address(addressNumber, addressStreet, addressPostalCode, addressCity, addressCountry);
                CRE result = ServicesPouloumer.signUp(lastName, firstName, nickName, email, password, false, false, gender, birthDate, phoneNumber, address);
                if (result != CRE_OK) {
                    if (result == CRE_ERR_EMAIL) {
                        resultErrorMessage = "This email is already used.";
                    } else if (result == CRE_ERR_NICKNAME) {
                        resultErrorMessage = "This nickname is already used.";
                    } else if (result == CRE_ERR_PASSWORD) {
                        resultErrorMessage = "This password is too weak.";
                    }
                }
            } /////////////////
            //////Update profile
            /////////////////
            else if ("updateUserDetails".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");

                String nickname = request.getParameter("nickname");
                String email = request.getParameter("mail");
                String password = request.getParameter("password");
                Date birthdate = DateUtil.toDate(request.getParameter("birthDate"));
                String phone = request.getParameter("phoneNumber");

                String addressNumber = request.getParameter("addressNumber");
                String addressStreet = request.getParameter("addressStreet");
                String addressPostalCode = request.getParameter("addressPostalCode");
                String addressCity = request.getParameter("addressCity");
                String addressCountry = request.getParameter("addressCountry");

                Address address = ServicesAddress.createAddress(addressNumber, addressStreet, addressPostalCode, addressCity, addressCountry);

                String temp = request.getParameter("gender");
                char gender = (temp == null ? p.getGender() : temp.charAt(0));

                boolean isModerator = p.isModerator();
                boolean isAdministrator = p.isAdministrator();

                CRE result = ServicesPouloumer.updatePouloumer(p, lastName, firstName, nickname, email, password, isModerator, isAdministrator, gender, birthdate, phone, address);
                if (result != CRE_OK) {
                    if (result == CRE_ERR_EMAIL) {
                        resultErrorMessage = "This email is already used.";
                    } else if (result == CRE_ERR_NICKNAME) {
                        resultErrorMessage = "This nickname is already used.";
                    } else if (result == CRE_ERR_PASSWORD) {
                        resultErrorMessage = "This password is too weak.";
                    }
                }
            } //////////
            ////consult home page
            //////////
            else if ("getUserEvents".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                JsonArray array = new JsonArray();
                for (Event e : p.getEvents()) {
                    if (!(e.isCancelled() || e.isFinished())) {
                        array.add(e.toJson());
                    }
                }
                container.add("events", array);
            } ///////////
            ////Consult profile
            ///////////
            else if ("getUserEventsHistory".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                JsonArray array = new JsonArray();
                for (Event e : p.getEvents()) {
                    if (e.isFinished()) {
                        array.add(e.toJson());
                    }
                }
                container.add("eventsHistory", array);
            } ///////////
            ////Consult friends
            ///////////
            else if ("getUserFriends".equals(sma)) {
                //TODO
            } ///////////
            ////Consult blacklist
            ///////////
            else if ("getUserBlacklist".equals(sma)) {
                //TODO
            } ///////////
            ////Add interests
            ///////////
            else if ("addInterestsToUser".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                List<Long> idActivities = new ArrayList<>();
                String[] values = request.getParameterValues("idActivities");
                for (String value : values) {
                    idActivities.add(Long.parseLong(value));
                }

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                List<Activity> interests = new ArrayList<>();
                for (Long idAct : idActivities) {
                    interests.add(ServicesActivity.getActivityById(idAct));
                }

                ServicesPouloumer.addInterests(p, interests);
            } ///////////
            ////Remove interest
            ///////////
            else if ("removeInterestFromUser".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));
                Long idActivity = Long.parseLong(request.getParameter("idActivity"));
                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Activity a = ServicesActivity.getActivityById(idActivity);

                ServicesPouloumer.removeInterest(p, a);
            }  ///////////
            ////Consult details
            ///////////
            else if ("getUserDetails".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);

                container.add("pouloumer", p.toJson());
            } ///////////
            ////Accept friend
            ///////////
            else if ("acceptFriend".equals(sma)) {
                //TODO
            } ///////////
            ////Remove friend
            ///////////
            else if ("removeFriend".equals(sma)) {
                //TODO
            } ///////////
            ////Remove blacklist
            ///////////
            else if ("removeFromBlackList".equals(sma)) {
                //TODO
            } ///////////
            ////Add blacklist
            ///////////
            else if ("addToBlacklist".equals(sma)) {
                //TODO
            } ///////////
            ////Request friend
            ///////////
            else if ("sendFriendRequest".equals(sma)) {
                //TODO
            } ///////////
            ////Report
            ///////////
            else if ("reportAbusiveBehaviour".equals(sma)) {
                //TODO
            } /////////////
            /////Search for an event
            /////////////
            else if ("simpleSearchForUser".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));
                
                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                List<Activity> interests = p.getInterests();
                
                Map<Event,List<Pouloumer>> availableEvents = ServicesEvent.getEventByInterests(interests);
                
                JsonArray events = new JsonArray();
                
                for (Event e : availableEvents.keySet())
                {
                    // This object wraps <idEvent, Event, list<IdUser, User,int(UserSimilarity)>,int(UserSimilarity)>
                    JsonObject eventAndPouloumerSimiliarities = new JsonObject();
                    
                    List<Pouloumer> participants = availableEvents.get(e);
                    
                    int averagePouloumerSimilarity = 0;
                    
                    // This array corresponds to the list<IdUser,User,int(UserSimilarity)>
                    JsonArray currentEventParticipants = new JsonArray();
                    
                    for (Pouloumer currentPouloumer : participants)
                    {
                        // This object wraps <IdUser,User,int(UserSimilarity)>
                        JsonObject participantSimilarity = new JsonObject();
                                                
                        long PouloumerSimilarity = p.getPouloumerSimilarity(currentPouloumer.getInterests());
                                                
                        participantSimilarity.add("pouloumer",currentPouloumer.toJson());
                        participantSimilarity.addProperty("similarity",PouloumerSimilarity);
                        
                        currentEventParticipants.add(participantSimilarity);
                        
                        averagePouloumerSimilarity += PouloumerSimilarity;
                    }
                    
                    eventAndPouloumerSimiliarities.add("event", e.toJson());
                    eventAndPouloumerSimiliarities.add("participants", currentEventParticipants);
                    eventAndPouloumerSimiliarities.addProperty("average_similarity", averagePouloumerSimilarity);
                    
                    events.add(eventAndPouloumerSimiliarities);
                }
                
                container.add("similarEvents", events);
            } ///////////
            ////Join event
            ///////////
            else if ("joinEvent".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));
                Long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                //TODO check that event isn't cancelled, already done, ...
                //TODO check that event isn't full
                //TODO check that user doesn't have other events at the same time
                ServicesPouloumer.joinEvent(p, e);
            } ///////////
            ////Leave event
            ///////////
            else if ("leaveEvent".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));
                Long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                ServicesEvent.removeParticipant(e, p);
            } //////////////
            /////Set up an event
            //////////////
            else if ("createEvent".equals(sma)) {
                // Get organizer
                Long idOrganizer = Long.parseLong(request.getParameter("idUser"));
                Pouloumer organizer = ServicesPouloumer.getPouloumerById(idOrganizer);

                // Get activity
                Long idActivity = Long.parseLong(request.getParameter("idActivity"));
                Activity activity = ServicesActivity.getActivityById(idActivity);

                // Create the address
                String addressNumber = request.getParameter("addressNumber");
                String addressStreet = request.getParameter("addressStreet");
                String addressPostalCode = request.getParameter("addressPostalCode");
                String addressCity = request.getParameter("addressCity");
                String addressCountry = request.getParameter("addressCountry");

                Address a = ServicesAddress.createAddress(addressNumber, addressStreet, addressPostalCode, addressCity, addressCountry);

                // Create the actual event
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Date startDate = DateUtil.toDate(request.getParameter("date"));
                int duration = Integer.parseInt(request.getParameter("duration"));
                int playerMin = Integer.parseInt(request.getParameter("playerMin"));
                int playerMax = Integer.parseInt(request.getParameter("playerMax"));

                ServicesEvent.createEvent(name, description, startDate, duration, a, activity, organizer, playerMin, playerMax);
            } ///////////
            ////Update event
            ///////////
            else if ("updateEvent".equals(sma)) {
                Long idEvent = Long.parseLong(request.getParameter("idEvent"));

                // TODO maybe allow to update name/description as well
                // String name = request.getParameter("name");
                // String description = request.getParameter("description");
                Date startDate = DateUtil.toDate(request.getParameter("date"));
                int duration = Integer.parseInt(request.getParameter("duration"));
                Long idAddress = Long.parseLong(request.getParameter("idAddress"));
                int playerMin = Integer.parseInt(request.getParameter("playerMin"));
                int playerMax = Integer.parseInt(request.getParameter("playerMax"));

                //TODO make sure only the organizer can do this
                Event event = ServicesEvent.getEventById(idEvent);
                Address address = ServicesAddress.getAddressById(idAddress);
                ServicesEvent.updateEvent(event, startDate, duration, address, playerMin, playerMax);
            } ///////////
            ////Cancel event
            ///////////
            else if ("cancelEvent".equals(sma)) {
                Long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Event event = ServicesEvent.getEventById(idEvent);

                //TODO make sure only the organizer can do this
                ServicesEvent.cancelEvent(event);
            } ////////////
            /////Consult someone else profile
            ////////////
            else if ("getOrganizedEvents".equals(sma)) {
                Long idUser = Long.parseLong(request.getParameter("idUser"));

                List<Event> organizedEvents = ServicesEvent.getOrganizedEvents(idUser);

                JsonArray organizedEventsArray = new JsonArray();

                for (Event e : organizedEvents) {
                    organizedEventsArray.add(e.toJson());
                }

                container.add("organizedEvents", organizedEventsArray);
            } ///////////////
            /////Consult finished event
            ///////////////
            else if ("addCommentToEvent".equals(sma)) {
                Date now = DateUtil.DateNow();
                String description = request.getParameter("description");
                long idUser = Long.parseLong(request.getParameter("idUser"));
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                ServicesComment.createComment(e, p, description, now);
            } ///////////////
            /////Consult an activity
            ///////////////
            else if ("findAllActivities".equals(sma)) {
                JsonArray array = new JsonArray();

                List<Activity> activities = ServicesActivity.findAllActivities();

                for (Activity a : activities) {
                    array.add(a.toJson());
                }

                container.add("activities", array);
            } ///////////
            ////Consult activity
            ///////////
            else if ("getActivityDetails".equals(sma)) {
                Long idActivity = Long.parseLong(request.getParameter("idActivity"));

                Activity a = ServicesActivity.getActivityById(idActivity);

                container.add("Activity", a.toJson());
            } /////////////////
            //////Consult an event
            /////////////////
            else if ("getEventDetails".equals(sma)) {
                Long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Event e = ServicesEvent.getEventById(idEvent);

                container.add("event", e.toJson());
            } /////////////////
            //////SMA name mistake?
            /////////////////
            else {
                Logger.getLogger(ServicesServlet.class.getName()).log(Level.SEVERE, "Unknown SMA: " + sma);
                serviceCalled = false;
            }
        } catch (NumberFormatException ex) {
            // occurs when parseLong on null or String not representing numbers
            resultErrorMessage = "Incorrect id given.";
        } catch (Exception ex) {
            resultErrorMessage = "Error when trying to process the transaction.";
            Logger.getLogger(ServicesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultErrorMessage.isEmpty()) {
            container.addProperty("result", "OK");
        } else {
            container.addProperty("result", "KO");
            container.addProperty("message", resultErrorMessage);
        }

        if (serviceCalled) {
            JsonServletHelper.printJsonOutput(response, container);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service SMA '" + sma + "' not found");
        }
    }
}
