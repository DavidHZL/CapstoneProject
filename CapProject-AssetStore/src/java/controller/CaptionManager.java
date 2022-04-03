/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import data.AccountProfileDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dadvid
 */
public class CaptionManager extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        
        ArrayList<String> errorList = new ArrayList();
        String errorListJSON;
        
        int profileID = Integer.parseInt(request.getParameter("profileID"));
        String newCaption = request.getParameter("newCaption");
        
        try {
            AccountProfileDB.addProfileCaption(newCaption, profileID);
            
            responseOut.flush();
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
            errorListJSON = gson.toJson(errorList);
            responseOut.println(errorListJSON);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
