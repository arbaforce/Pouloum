/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.badge;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.service.ServicesApp;
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
            String som = null;

            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                som = pathInfo.substring(1);
            }

            String somParameter = request.getParameter("SOM");
            if (somParameter != null) {
                som = somParameter;
            }

            JsonObject container = new JsonObject();

            PouloumSOM service = new PouloumSOM(container);
    }
}
