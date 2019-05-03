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

        //TODO : mettre dans le main
        JpaUtil.init();

        
        try {
            //////////
            ////login
            //////////
            if ("login".equals(sma)) {
                String mail = request.getParameter("mail");
                String nickName = request.getParameter("nickName");
                String password = request.getParameter("password");

                Pouloumer p;
                if (mail != null) {
                    p = ServicesPouloumer.loginWithMail(mail, password);
                } else {
                    p = ServicesPouloumer.loginWithNickname(nickName, password);
                }
                if (p != null) {
                    container.add("Pouloumer", g.toJsonTree(p, Pouloumer.class));
                } else {
                    container.addProperty("error", "There is no match for these identifiants.");
                }
            }
            //////////
            ////signUp
            //////////
            else if ("signUp".equals(sma)) {
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String nickName = request.getParameter("nickName");
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
                    container.addProperty("created", true);
                } catch (Exception ex) {
                    container.addProperty("created", false);
                    throw ex;
                }
            }
            //////////
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
                } else {
                    container.addProperty("error", "id is invalid");
                }
            } else if ("getUserBadges".equals(sma)) {
                //TODO when badges are implemented.
            }
            ///////////
            ////Consult profile
            ///////////
            else if ("getUserEventsHistory".equals(sma)) {

            } else if ("getUserFriends".equals(sma)) {

            } else if ("getUserBlacklist".equals(sma)) {

            } else if ("addInterestsToUser".equals(sma)) {

            } else if ("removeInterestsToUser".equals(sma)) {

            } else if ("getUserInterests".equals(sma)) {

            } else if ("getUserDetails".equals(sma)) {

            } else if ("acceptFriend".equals(sma)) {

            } else if ("removeFriend".equals(sma)) {

            } else if ("removeFromBlackList".equals(sma)) {

            }
            ////////////
            /////Consult someone else profile
            ////////////
            else if ("addToBlacklist".equals(sma)) {

            } else if ("sendFriendRequest".equals(sma)) {

            } else if ("reportAbusiveBehaviour".equals(sma)) {

            }
            /////////////
            /////Search for an event
            /////////////
            else if ("simpleSearchForUser".equals(sma)) {

            } else if ("joinEvent".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                ServicesEvent.addParticipant(p, e);
                CRE result = ServicesPouloumer.joinEvent(p, e);
                //FIXME make use of result
            } else if ("leaveEvent".equals(sma)) {
                long idUser = Long.parseLong(request.getParameter("idUser"));
                long idEvent = Long.parseLong(request.getParameter("idEvent"));

                Pouloumer p = ServicesPouloumer.getPouloumerById(idUser);
                Event e = ServicesEvent.getEventById(idEvent);

                CRE pouloumerResult = ServicesPouloumer.leaveEvent(p, e);

                if (pouloumerResult != CRE.CRE_OK) {
                    // Throw exception to cancel the rest of the removal
                    throw new Exception("ERROR: Error when processing the transaction to remove event from user.");
                }

                CRE eventResult = ServicesEvent.removeParticipant(p, e);

                if (eventResult != CRE.CRE_OK) {
                    throw new Exception("ERROR: Error when processing the transaction to remove user from event.");
                }
                //TODO decide which association to keep between event and user to avoid this double transaction problem
            }
            //////////////
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
            } else if ("updatedEvent".equals(sma)) {

            } else if ("cancelEvent".equals(sma)) {

            } else if ("getOrganizedEvents".equals(sma)) {

            }
            ///////////////
            /////Consult finished event
            ///////////////
            else if ("addCommentToEvent".equals(sma)) {

            }
            ///////////////
            /////Consult an activity
            ///////////////
            else if ("findAllActivities".equals(sma)) {

            } else if ("getActivityDetails".equals(sma)) {

            }
            /////////////////
            //////Consult an event
            /////////////////
            else if ("getEventDetails".equals(sma)) {

            }
            /////////////////
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
        
        
        //TODO : mettre dans le main
        JpaUtil.destroy();

        if (serviceCalled) {
            JsonServletHelper.printJsonOutput(response, container);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service SMA '" + sma + "' not found");
        }
    }
}
