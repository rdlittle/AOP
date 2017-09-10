/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rlittle
 */
@WebServlet("/printdoc")
public class TextServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setBufferSize(8192);
        Map<String, String[]> map = request.getParameterMap();
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">"
                    + "<head><title>Monitor Request</title></head>");

            // then write the data of the response
            out.println("<body  bgcolor=\"#ffffff\">"
                    + "<h3>Monitor Request</h3>");
            for (String key : map.keySet()) {
                out.println(key + ": ");
            }

            out.println("</body></html>");
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setBufferSize(8192);
        Map<String, String[]> map = request.getParameterMap();
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">"
                    + "<head><title>Monitor Request</title></head>");

            // then write the data of the response
            out.println("<body  bgcolor=\"#ffffff\">"
                    + "<h3>Monitor Request</h3>");
            for (String key : map.keySet()) {
                out.println(key + ": ");
            }

            out.println("</body></html>");
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    @Override
    public String getServletInfo() {
        return "The monitor request servlet response";

    }
}
