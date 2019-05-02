/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.activity;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.util.JsonServletHelper;
import com.mycompany.pouloum.util.exception.DBException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Martin
 */
public class PouloumSOMServlet extends HttpServlet {

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

        try {

            String som = null;

            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                som = pathInfo.substring(1);
            }

            String somParameter = request.getParameter("SOM");
            if (somParameter != null) {
                som = somParameter;
            }

            DBConnection connection = new DBConnection(
                    this.getInitParameter("JDBC-Pouloum-URL"),
                    this.getInitParameter("JDBC-Pouloum-User"),
                    this.getInitParameter("JDBC-Pouloum-Password"),
                    "Activity"
            );
            
            JsonObject container = new JsonObject();

            PouloumSOM service = new PouloumSOM(connection, container);

        } catch (DBException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB Exception: " + ex.getMessage());
            this.getServletContext().log("DB Exception in " + this.getClass().getName(), ex);
        }
    }
}
