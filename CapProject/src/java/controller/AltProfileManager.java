/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.function.PostManagement;
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
import model.Post;

/**
 *
 * @author Dadvid
 */
public class AltProfileManager extends HttpServlet {

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

        HttpSession session = request.getSession();
        ArrayList<String> errorList = new ArrayList();

        Gson gson = new Gson();
        String errorListJSON;

        Account currentUser = (Account) session.getAttribute("currentUser");
        int creatorID = Integer.parseInt(request.getParameter("creatorID"));

        String url = "";
        if (currentUser.getAccountID() != creatorID) {
            try {
                model.Profile altProfile = ProfileManagement.retrieveAltProfile(creatorID, errorList);
                ArrayList<Post> altUserPostList = PostManagement.retrieveAllPostsByProfileID(altProfile, errorList);

                altProfile.posts = altUserPostList;

                request.setAttribute("altProfile", gson.toJson(altProfile));

                url = "/page/profile/altProfile.jsp";
                
            } catch (SQLException ex) {
                errorList.add("Error Retrieving current user profile.");
                errorListJSON = gson.toJson(errorList);
                responseOut.println(errorListJSON);
            }
        } else {
            url = "/page/profile/profile.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
