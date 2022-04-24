/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.function.PostManagement;
import controller.function.ProfileManagement;
import controller.function.SearchManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class Trending extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        ArrayList<Post> trendingPosts = PostManagement.retrieveTrendingPosts(errorList);
        
        String trendingPostListJSON = gson.toJson(trendingPosts);
        
        responseOut.println(trendingPostListJSON);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        ArrayList<Profile> trendingProfiles = ProfileManagement.retrieveTrendingProfiles(errorList);
        
        String trendingProfileListJSON = gson.toJson(trendingProfiles);
        
        responseOut.println(trendingProfileListJSON);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
