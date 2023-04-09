/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;
import com.resumeholic.business.LoginBusiness;
import com.resumeholic.helper.UserInfo;

/**
 *
 * @author student
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = Common.isAuthenticated(request).getKey();
        String uemail = Common.isAuthenticated(request).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (cookieUser != null) {
            request.setAttribute("user", cookieUser);
            response.sendRedirect(request.getContextPath() + "/Resumes");
            return;
        }
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserInfo foundUser = LoginBusiness.find(email, password);
        
        if (foundUser != null) {
            request.setAttribute("user", foundUser);
            token = Common.getAutho().createJWT("FrontEnd", email, 100000);

            Cookie newCookie = new Cookie(Common.getAuthenticationCookieName(), token);
            response.addCookie(newCookie);
            
            response.sendRedirect(request.getContextPath() + "/Resumes");
        } else {
            RequestDispatcher requestDispatcher = request.
                    getRequestDispatcher("loginfailed.jsp");

            requestDispatcher.forward(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = Common.isAuthenticated(req).getKey();
        String uemail = Common.isAuthenticated(req).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (token != null && cookieUser != null) {
            req.setAttribute("user", cookieUser);
            resp.sendRedirect(req.getContextPath() + "/Resumes");
        } else {
            resp.sendRedirect(req.getContextPath());
        }
    }
    
    
}
