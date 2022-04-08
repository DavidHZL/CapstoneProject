/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.function.PostManagement;
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
import model.Post;

/**
 *
 * @author Dadvid
 */
public class EditPost extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Delete Post
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        
        int postID = Integer.parseInt(request.getParameter("postID"));
        
        try {
            model.Profile editedProfile = PostManagement.deletePost(currentUser, postID, errorList);
            
            String userProfileJSON = gson.toJson(editedProfile);

            responseOut.println(userProfileJSON);
        } catch(SQLException ex) {
            errorList.add(ex.getMessage());
            String errorListJSON = gson.toJson(errorList);
            responseOut.println(errorListJSON);
            
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Edit Post
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        
        int postID = Integer.parseInt(request.getParameter("editedPostID"));
        String postCaption = request.getParameter("editedPostCaption");
        String postDescription = request.getParameter("editedPostDescription");
        
        model.Profile editedProfile = PostManagement.editPost(currentUser, postID, postCaption, postDescription, errorList);
        
        if(errorList.isEmpty()){
            String userProfileJSON = gson.toJson(editedProfile);

            responseOut.println(userProfileJSON);
        } else {
            String errorListJSON = gson.toJson(errorList);
            
            responseOut.println(errorListJSON);
        }
        
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
