/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.ihm.web;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.JsonServletHelper;
import java.io.IOException;
import java.io.PrintWriter;
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



        ajaxAction.release();

    }
}
