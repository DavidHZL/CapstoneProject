/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.function.Authorization;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url;
        
        HttpSession session = request.getSession();
        ArrayList<String> errorList = new ArrayList();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String passwordCheck = request.getParameter("passwordCheck");
        String accountType = request.getParameter("accountType");

        if (Authorization.IsValidRegister(username, password, email, errorList)) {
            Account newUser = Authorization.RegisterUser(username, password, passwordCheck, email, accountType, errorList);
            if (newUser != null) {
                session.setAttribute("currentUser", newUser);
                url = "/page/auth/login.jsp";
            } else {
                url = "/page/auth/register.jsp";
            }
        } else {
            url = "/page/auth/register.jsp";
        }
        
        request.setAttribute("errorList", errorList);
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
