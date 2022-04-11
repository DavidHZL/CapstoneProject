/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dadvid
 */
public class LikeManagement extends HttpServlet {

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

        ArrayList<String> errorList = new ArrayList();
        Gson gson = new Gson();
        
        int postID = Integer.parseInt(request.getParameter("postID"));
        int likes = Integer.parseInt(request.getParameter("likes"));
        
        errorList.add("test");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
