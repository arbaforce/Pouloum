/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.sma;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Martin
 */
@WebServlet(name = "PouloumSMServlet", urlPatterns = {"/PouloumSMServlet"})
public class PouloumSMServlet extends HttpServlet {

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
            
            String somParameter = request.getParameter("SMA");
            if (somParameter != null) {
                sma = somParameter;
            }

            JsonObject container = new JsonObject();

            PouloumSM service = new PouloumSM(
                    this.getInitParameter("URL-SOM-User"),
                    this.getInitParameter("URL-SOM-Badge"),
                    this.getInitParameter("URL-SOM-Address"),
                    this.getInitParameter("URL-SOM-Activity"),
                    this.getInitParameter("URL-SOM-Event"),
                    container );
    }
}
