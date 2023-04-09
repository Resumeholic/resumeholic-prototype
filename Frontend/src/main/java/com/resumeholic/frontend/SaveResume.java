/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.frontend;

import com.resumeholic.business.LoginBusiness;
import com.resumeholic.business.Business;
import com.resumeholic.helper.PersonalInfoHeader;
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
 * Servlet responsible for saving user's resume
 * @author Caleb Lam
 */
@WebServlet(name = "SaveResume", urlPatterns = {"/Save"})
public class SaveResume extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO: Actually save the resume...
        String token = Common.isAuthenticated(request).getKey();
        String uemail = Common.isAuthenticated(request).getValue();
        UserInfo cookieUser = LoginBusiness.lookup(uemail);
        if (token != null && cookieUser != null) {
            request.setAttribute("user", cookieUser);
        } else {
            response.sendRedirect(request.getContextPath());
            return;
        }
        
        ResumeInfo resume = (ResumeInfo)request.getSession().getAttribute("currentResume");
        PersonalInfoHeader header = resume.getPersonalInfoHeader();
        String documentName = request.getParameter("documentName");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        
        resume.setDocumentName(documentName);
        header.setFirstName(firstName);
        header.setLastName(lastName);
        header.setEmail(email);
        header.setPhoneNumber(phoneNumber);
        
        // TODO: Save other sections of the resume!
        // (May require bringing this outside of this course's labs...)
        
        String section = request.getParameter("section") != null
                ? (String)request.getParameter("section") : "";
        boolean saveSuccess;
        switch (section) {
            case "document":
                System.out.println("Saving " + section);
                saveSuccess = Business.saveDocumentInfo(token, resume);
                break;
            case "header":
                System.out.println("Saving " + header);
                saveSuccess = Business.saveHeader(token, header);
                break;
            default:
                System.out.println("Saving all sections...");
                saveSuccess = Business.saveResume(token, resume);
        }
        
        
        RequestDispatcher rd = saveSuccess
                ? request.getRequestDispatcher("resumesaved.jsp") 
                : request.getRequestDispatcher("resumesavefailed.jsp");
        
        if (saveSuccess) {
            // refresh resume info
            request.setAttribute("resumesInfo", Business.getResumes(cookieUser.getId(), token).getResumes());
        }
        rd.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
