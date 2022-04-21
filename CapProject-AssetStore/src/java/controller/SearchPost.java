/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.function.SearchManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;

/**
 *
 * @author Dadvid
 */
public class SearchPost extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        String errorListJSON;
        
        String criteria = request.getParameter("searchCriteria");
        
        ArrayList<Post> postList = SearchManagement.searchForPosts(criteria, errorList);
        
        String postListJSON = gson.toJson(postList);
        
        responseOut.println(postListJSON);
        
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
