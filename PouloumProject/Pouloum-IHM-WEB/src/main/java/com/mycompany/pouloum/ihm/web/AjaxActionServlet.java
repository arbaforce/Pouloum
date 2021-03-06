package com.mycompany.pouloum.ihm.web;

import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import static com.mycompany.pouloum.util.Common.isEmailValid;
import com.mycompany.pouloum.util.DateUtil;
import static com.mycompany.pouloum.util.GeoTest.getLatLng;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Martin
 */
@WebServlet(name = "AjaxActionServlet", urlPatterns = {"/AjaxActionServlet"})
public class AjaxActionServlet extends HttpServlet {

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

        HttpSession session = request.getSession(true);

        request.setCharacterEncoding(JsonServletHelper.ENCODING_UTF8);

        String action = request.getParameter("action");

        JsonObject container = new JsonObject();

        AjaxAction ajaxAction = new AjaxAction(this.getInitParameter("URL-SMA"), container);

        boolean actionCalled = true;

        try {
            if ("getUserIdSession".equals(action)) {
                String userID = (String) session.getAttribute("userID");
                container.addProperty("result", true);
                container.addProperty("userID", userID);
            } else if ("setEventIdSession".equals(action)){
                String eventID = request.getParameter("eventID");
                session.setAttribute("eventID", eventID);
            } else if ("getEventIdSession".equals(action)) {
                String eventID = (String) session.getAttribute("eventID");
                if(eventID == null){
                    container.addProperty("result", false);
                }
                else {
                    container.addProperty("result", true);
                    container.addProperty("eventID", eventID);
                }
            } else if ("removeEventIdSession".equals(action)){
                session.removeAttribute("eventID");
            } else if ("getActivityIdSession".equals(action)) {
                String activityID = (String) session.getAttribute("activityID");
                container.addProperty("result", true);
                container.addProperty("activityID", activityID);
            } else if ("getLatLng".equals(action)){
                String country = request.getParameter("country");
                String city = request.getParameter("city");
                String postal_code = request.getParameter("postal_code");
                String street = request.getParameter("street");
                String number = request.getParameter("number");
                String label = request.getParameter("label");
                String id = request.getParameter("id");
                
                LatLng point = getLatLng(number + " " + street + " " + postal_code + " " + city + " " + country);
                
                container.addProperty("result", true);
                container.addProperty("lat", point.lat);
                container.addProperty("lng", point.lng);
                container.addProperty("label", label);
                container.addProperty("id", id);
            }else if ("login".equals(action)) {
                String id = request.getParameter("id");
                String password = request.getParameter("password");

                if (id.equals("") || password.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : veuillez remplir tous les champs");
                } else if (isEmailValid(id)) {
                    ajaxAction.loginByMail(id, password);
                    if (container.get("result").getAsBoolean()) {
                        session.setAttribute("userID", container.get("userID").getAsString());
                    }
                } else {
                    ajaxAction.loginByNickname(id, password);
                    if (container.get("result").getAsBoolean()) {
                        session.setAttribute("userID", container.get("userID").getAsString());
                    }
                }
            } else if ("signUp".equals(action)) {
                String nickName = request.getParameter("pseudo").trim();
                String email = request.getParameter("mail").trim();
                String emailConfirmation = request.getParameter("mailConfirmation").trim();
                String password = request.getParameter("password");
                String passwordConfirmation = request.getParameter("passwordConfirmation");
                //TODO see if other fields are mandatory

                if (email.equals("") || emailConfirmation.equals("") || password.equals("") || passwordConfirmation.equals("") || nickName.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : veuillez remplir tous les champs obligatoires");
                } else if (!isEmailValid(email)) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : l'addresse mail n'est pas valide");
                } else if (!email.equals(emailConfirmation)) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : l'addresse mail de confirmation ne correspond pas");
                } else if (!password.equals(passwordConfirmation)) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : le mot de passe de confirmation ne correspond pas");
                } else {
                    String lastName = request.getParameter("surname").trim();
                    String firstName = request.getParameter("name").trim();
                    char gender = request.getParameter("gender").charAt(0);
                    String birthDate = request.getParameter("birthDate");
                    String phoneNumber = request.getParameter("phoneNumber").trim();

                    String address = request.getParameter("address").trim();
                    String addressPostalCode = request.getParameter("addressPostalCode").trim();
                    String addressCity = request.getParameter("addressCity").trim();
                    String addressCountry = request.getParameter("addressCountry").trim();

                    String addressNumber = "";
                    while (Character.isDigit(address.charAt(0))) {
                        addressNumber += address.charAt(0);
                        address = address.substring(1);
                    }
                    String addressStreet = address.trim();

                    ajaxAction.signUp(lastName, firstName, nickName, email, password, false,
                            false, gender, birthDate, phoneNumber, addressNumber, addressStreet,
                            addressPostalCode, addressCity, addressCountry);
                }
            } else if ("getUserEvents".equals(action)) {
                String id = request.getParameter("id");
                String history = request.getParameter("history");
                ajaxAction.getUserEvents(id, history);
            } else if ("getUserBadges".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.getUserBadges(id);
            } else if ("getUserDetails".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.getUserDetails(id);
            } else if ("updateUserDetails".equals(action)) {
                String id = request.getParameter("id");
                String surname = request.getParameter("surname").trim();
                String name = request.getParameter("name").trim();
                String pseudo = request.getParameter("pseudo").trim();
                String password = request.getParameter("password");
                String birthdate = request.getParameter("birthdate");
                String mail = request.getParameter("mail").trim();
                String phone_number = request.getParameter("phone_number").trim();
                String country = request.getParameter("country").trim();
                String city = request.getParameter("city").trim();
                String postal_number = request.getParameter("postal_number").trim();
                String address = request.getParameter("address").trim();
                String gender = request.getParameter("gender");

                if (!isEmailValid(mail)) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : l'adresse mail indiquée est invalide");
                } else {
                    String street_number = "";
                    while (Character.isDigit(address.charAt(0))) {
                        street_number += address.charAt(0);
                        address = address.substring(1);
                    }
                    String street = address.trim();

                    ajaxAction.updateUserDetails(id, surname, name, gender, pseudo, password, birthdate, mail, phone_number, country, city, postal_number, street, street_number);
                }
            } else if ("addInterest".equals(action)) {
                String id = request.getParameter("id");
                String interest = request.getParameter("interest").trim();
                if (interest.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : veuillez remplir le champ");
                } else {
                    ajaxAction.addInterest(id, interest);
                }
            } else if ("removeInterest".equals(action)) {
                String id = request.getParameter("id");
                String interest = request.getParameter("interest").trim();
                
                if (interest.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : activité à retirer non trouvé");
                } else {
                    ajaxAction.removeInterest(id, interest);
                }                
            } else if ("updateEvent".equals(action)) {
                String id = request.getParameter("id");
                String name = request.getParameter("name").trim();
                String description = request.getParameter("description").trim();
                String startDate = request.getParameter("startDate");
                String duration = request.getParameter("duration").trim();
                
                // Get address number and street
                String address = request.getParameter("address").trim();
                String addressNumber = "";
                while (Character.isDigit(address.charAt(0))) {
                    addressNumber += address.charAt(0);
                    address = address.substring(1);
                }
                String addressStreet = address.trim();

                String addressPostalCode = request.getParameter("addressPostalCode").trim();
                String addressCity = request.getParameter("addressCity").trim();
                String addressCountry = request.getParameter("addressCountry").trim();
                String participantsMin = request.getParameter("playerMin").trim();
                String participantsMax = request.getParameter("playerMax").trim();

                ajaxAction.updateEvent(id, name, description, startDate, duration,
                        addressNumber, addressStreet, addressPostalCode,
                        addressCity, addressCountry, participantsMin, participantsMax);
            } else if ("createEvent".equals(action)) {
                String idOrganizer = request.getParameter("idOrganizer");
                String idActivity = request.getParameter("idActivity");
                String name = request.getParameter("name").trim();
                String description = request.getParameter("description").trim();
                String startDate = request.getParameter("startDate");
                String duration = request.getParameter("duration").trim();
                
                // Get address number and street
                String address = request.getParameter("address").trim();
                String addressNumber = "";
                while (Character.isDigit(address.charAt(0))) {
                    addressNumber += address.charAt(0);
                    address = address.substring(1);
                }
                String addressStreet = address.trim();
                
                String addressPostalCode = request.getParameter("addressPostalCode").trim();
                String addressCity = request.getParameter("addressCity").trim();
                String addressCountry = request.getParameter("addressCountry").trim();
                String participantsMin = request.getParameter("playerMin").trim();
                String participantsMax = request.getParameter("playerMax").trim();

                ajaxAction.createEvent(name, description, startDate, duration,
                        addressNumber, addressStreet, addressPostalCode,
                        addressCity, addressCountry, idActivity, idOrganizer,
                        participantsMin, participantsMax);
            } else if ("cancelEvent".equals(action)) {
                String eventID = request.getParameter("eventID");
                ajaxAction.cancelEvent(eventID);
            } else if ("getEventDetails".equals(action)) {
                String eventID = request.getParameter("eventID");
                ajaxAction.getEventDetails(eventID);
            } else if ("getActivityDetails".equals(action)) {
                String activityID = request.getParameter("activityID");
                ajaxAction.getActivityDetails(activityID);
            } else if ("getActivityTree".equals(action)) {
                ajaxAction.findAllActivities();
            } else if ("getAllEvents".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.findAllEvents(id);
            } else if ("getOrganizedEvents".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.findOrganizedEvents(id);
            }  else if ("joinEvent".equals(action)) {
                String userID = request.getParameter("userID");
                String eventID = request.getParameter("eventID");
                ajaxAction.joinEvent(userID, eventID);
            } else if ("leaveEvent".equals(action)) {
                String userID = request.getParameter("userID");
                String eventID = request.getParameter("eventID");
                ajaxAction.leaveEvent(userID, eventID);
            } else {
                actionCalled = false;
            }

        } catch (ServiceException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Service Exception: " + ex.getMessage());
            this.getServletContext().log("Service Exception in " + this.getClass().getName(), ex);
        }

        ajaxAction.release();

        if (actionCalled) {
            //container.addProperty("IHM_microCAS_User", (String) session.getAttribute(MicroCasFilter.SESSION_MICROCAS_CLIENT_VALID_LOGIN));
            JsonServletHelper.printJsonOutput(response, container);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action '" + action + "' inexistante");
        }

    }
}
