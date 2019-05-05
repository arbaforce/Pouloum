package com.mycompany.pouloum.ihm.web;

import com.google.gson.JsonObject;
import static com.mycompany.pouloum.util.Common.isEmailValid;
import com.mycompany.pouloum.util.DateUtil;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

            if ("login".equals(action)) {
                String id = request.getParameter("id");
                String password = request.getParameter("password");

                if (id.equals("") || password.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : veuillez remplir tous les champs");
                } else if (isEmailValid(id)) {
                    ajaxAction.loginByMail(id, password);
                    if(container.get("result").getAsBoolean()){
                        Cookie cookie = new Cookie("userID", container.get("user").getAsString());
                        response.addCookie(cookie);
                    }
                } else {
                    ajaxAction.loginByNickName(id, password);
                    if(container.get("result").getAsBoolean()){
                        Cookie cookie = new Cookie("userID", container.get("userID").getAsString());
                        response.addCookie(cookie);
                    }
                }
            } else if ("signUp".equals(action)) {
                String nickName = request.getParameter("nickname");
                String email = request.getParameter("mail");
                String password = request.getParameter("password");
                //TODO : check whether other fields should be mandatory

                if (email.equals("") || password.equals("") || nickName.equals("")) {
                    container.addProperty("result", false);
                    container.addProperty("errorMessage", "ERREUR : veuillez remplir tous les champs obligatoires");
                } else {
                    String lastName = request.getParameter("lastName");
                    String firstName = request.getParameter("firstName");
                    char gender = request.getParameter("gender").charAt(0);
                    String birthDate = request.getParameter("birthDate");
                    String phoneNumber = request.getParameter("phoneNumber");

                    String addressNumber = request.getParameter("addressNumber");
                    String addressStreet = request.getParameter("addressStreet");
                    String addressPostalCode = request.getParameter("addressPostalCode");
                    String addressCity = request.getParameter("addressCity");
                    String addressCountry = request.getParameter("addressCountry");
                    ajaxAction.signUp(lastName, firstName, nickName, email, password, false,
                            false, gender, birthDate, phoneNumber, addressNumber, addressStreet,
                            addressPostalCode, addressCity, addressCountry);
                }
            } else if ("getUserEvents".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.getUserEvents(id);
            } else if ("getUserBadges".equals(action)) {
                String id = request.getParameter("id");
                ajaxAction.getUserBadges(id);
            }
            else if("getUserDetails".equals(action)){
                String id = request.getParameter("id");
                ajaxAction.getUserDetails(id);
            }
            else {
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
