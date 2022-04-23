/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.function.ProfileManagement;
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
public class Follow extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        int profileID = Integer.parseInt(request.getParameter("followingProfileID"));
        
        try {
            boolean followStatus = ProfileManagement.checkFollowStatus(currentUser, profileID, errorList);
            
             String followerJSON = gson.toJson(followStatus);
        
             responseOut.println(followerJSON);
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        
        int profileID = Integer.parseInt(request.getParameter("followingProfileID"));
        int followerCount = Integer.parseInt(request.getParameter("followerCount"));
        
        try {
            int newFollowerCount = ProfileManagement.addFollowerToProfile(currentUser, profileID, followerCount, errorList);
            
             String followerJSON = gson.toJson(newFollowerCount);
        
             responseOut.println(followerJSON);
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
        }
        
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
