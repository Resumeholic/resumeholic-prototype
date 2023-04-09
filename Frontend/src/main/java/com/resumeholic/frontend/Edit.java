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
@WebServlet(name = "Edit", urlPatterns = {"/Edit"})
public class Edit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = Common.isAuthenticated(req).getKey();
        String uemail = Common.isAuthenticated(req).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (token != null && cookieUser != null) {
            req.setAttribute("user", cookieUser);
        } else {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        
        try {
            ResumeInfo resume = (ResumeInfo)req.getSession().getAttribute("currentResume");
            if (resume == null) {
                int resumeId = Integer.parseInt(req.getParameter("id"));
                resume = Business.getResume(resumeId, token);
                
                if (resume.getAuthorId() != cookieUser.getId()) {
                    resp.sendRedirect(req.getContextPath() + "/Resumes");
                    return;
                }

                req.getSession().setAttribute("currentResume", resume);
            }
            
            if (resume != null) {
                RequestDispatcher rd = req.getRequestDispatcher("editresume.jsp");
                rd.forward(req, resp);
            } else {
                throw new NullPointerException("cannot find resume");
            }
            
        } catch(NumberFormatException | NullPointerException ex) {
            System.out.println(ex);
            System.out.println("Redirecting back to Resumes");
            resp.sendRedirect(req.getContextPath() + "/Resumes");
        }
    }


}
