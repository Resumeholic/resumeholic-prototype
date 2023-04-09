<%-- 
    Document   : EditResume
    Created on : Feb 16, 2023, 8:35:09 PM
    Author     : student
--%>

<%@page import="com.resumeholic.helper.ResumeInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%ResumeInfo resume = (ResumeInfo)request.getSession().getAttribute("currentResume");%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=resume.getDocumentName()%></title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <div class="container">
            <a class="btn" href="/Frontend/Resumes" style="margin-bottom: 1rem; margin-top: 1rem">Back to Resumes</a>
            
            <form action="Save" method="post">
                <div style="margin-bottom: 1rem;">
                    <span>Document Name <input type="text" name="documentName" value="<%=resume.getDocumentName()%>" /></span>
                    <input class="btn center" formaction="Save?section=document" type="submit" value="Save" name="save" />
                </div>
                
                <h4>Header</h4>
                <div style="margin-bottom: 1rem;">
                    <span>First Name <input type="text" name="fname" value="<%=resume.getPersonalInfoHeader().getFirstName()%>" /></span>
                    <span>Last Name <input type="text" name="lname" value="<%=resume.getPersonalInfoHeader().getLastName()%>" /></span>
                    <span>Email <input type="text" name="email" value="<%=resume.getPersonalInfoHeader().getEmail()%>" /></span>
                    <span>Phone Number <input type="text" name="phone" value="<%=resume.getPersonalInfoHeader().getPhoneNumber()%>" /></span>
                    <input class="btn center" formaction="Save?section=header" type="submit" value="Save" name="save" />
                </div>
                
                <div style="margin-bottom: 1rem;">
                    <input class="btn center" type="submit" value="Save All" name="save" />
                </div>
            </form>
        </div>
    </body>
</html>
