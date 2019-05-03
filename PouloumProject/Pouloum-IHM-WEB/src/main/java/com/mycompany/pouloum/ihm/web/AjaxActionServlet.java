/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mycompany.pouloum.ihm.web;

import com.google.gson.JsonObject;
import static com.mycompany.pouloum.util.Common.isEmailValid;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
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
            
            if ("login".equals(action)) {
                String id = request.getParameter("id");
                String password = request.getParameter("password");
                
                if(id.equals("") || password.equals("")){
                    container.addProperty("error", "ERROR : fill every box");
                }
                else if(isEmailValid(id)){
                    ajaxAction.loginByMail(id, password);
                }
                else {
                    ajaxAction.loginByNickName(id, password);
                    
                }
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
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action '" + action + "' inexistante");
        }
        
    }
}
