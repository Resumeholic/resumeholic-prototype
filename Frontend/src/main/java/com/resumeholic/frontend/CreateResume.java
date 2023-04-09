/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.frontend;

import com.resumeholic.business.Business;
import com.resumeholic.business.LoginBusiness;
import com.resumeholic.helper.ResumeInfo;
import com.resumeholic.helper.UserInfo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */

@WebServlet(name = "CreateResume", urlPatterns = {"/Create"})
public class CreateResume extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = Common.isAuthenticated(request).getKey();
        String uemail = Common.isAuthenticated(request).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (token != null && cookieUser != null) {
            request.setAttribute("user", cookieUser);
            RequestDispatcher requestDispatcher = request.
                    getRequestDispatcher("createresume.jsp");

            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = Common.isAuthenticated(request).getKey();
        String uemail = Common.isAuthenticated(request).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (token != null && cookieUser != null) {
            request.setAttribute("user", cookieUser);
        } else {
            response.sendRedirect(request.getContextPath());
            return;
        }
        
        String documentName = (String)request.getParameter("docname");
        String firstName = (String)request.getParameter("fname");
        String lastName = (String)request.getParameter("lname");
        String email = (String)request.getParameter("email");
        String phoneNumber = (String)request.getParameter("phonenumber");
        
        
        ResumeInfo createdResume = Business.createResume(token, cookieUser.getId(), documentName, firstName, lastName, email, phoneNumber);
        response.sendRedirect(request.getContextPath() + "/Edit?id=" + createdResume.getId());
    }

}
