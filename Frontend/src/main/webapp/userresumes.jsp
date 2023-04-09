<%-- 
    Document   : userresumes
    Created on : Feb 10, 2023, 9:30:05 PM
    Author     : student
--%>

<%@page import="java.time.format.FormatStyle"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="com.resumeholic.helper.ResumeInfo"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Resumes - Resumeholic</title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <% 
            ArrayList<ResumeInfo> resumes= (ArrayList<ResumeInfo>)request.getAttribute("resumesInfo");
        %>
        <div class="container">
            <h1 class="center">My Resumes</h1>
            <div class="center">
                The dates and times currently use the UTC time zone. This (unfortunately) is a limitation posed by Java Servlets.<br>
                The implementation of time zones will take place beyond this class! 😉
            </div>
            
<!--            <div class="container" style="padding: 1rem; border: 2px solid black; border-radius: 8px">-->
            <div class="container">
                <table class="striped">
                    <thead>
                        <th>Name</th>
                        <th>Created At</th>
                        <th>Updated At</th>
                    </thead>                   
                    <% for(ResumeInfo r : resumes) { %>
                        <tr>
                            <td><a href="/Frontend/Edit?id=<%=r.getId()%>"><%=r.getDocumentName()%></a></td>
                            <td><%=r.getCreatedDate().withZoneSameInstant(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))
                            %></td>
                            <td><%=r.getUpdatedDate().withZoneSameInstant(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))
                            %></td>
                        </tr>
                    <%}%>
                </table>
                
                <a style="margin-bottom: 1rem;" class="btn right" href="/Frontend/Create">New Resume</a>
            </div>
        </div>
    </body>
</html>