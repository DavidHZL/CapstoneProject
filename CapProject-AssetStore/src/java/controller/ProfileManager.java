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
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author Dadvid
 */
public class ProfileManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        ArrayList<String> errorList = new ArrayList();
        
        Gson gson = new Gson();
        String errorListJSON;
        
        try {
            model.Profile userProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);

            String userProfileJSON = gson.toJson(userProfile);

            responseOut.println(userProfileJSON);
        } catch (SQLException ex) {
            errorList.add("Error Retrieving current user profile.");
            errorListJSON = gson.toJson(errorList);
            responseOut.println(errorListJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
