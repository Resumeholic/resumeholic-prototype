<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Resume - Resumeholic</title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <div class="container">
            <h1 class="center">Resume Information</h1>
            <form action="Create" method="post" >
                <span>Document Name <input type="text" name="docname"></span>
                <span>First Name <input type="text" name="fname"></span>
                <span>Last Name <input type="text" name="lname"></span>
                <span>E-mail Address <input type="text" name="email"></span>
                <span>Phone Number <input type="text" name="phonenumber"></span>
                <input class="btn" style="display: block; margin-bottom: 1rem; margin-right: 0; margin-left: auto" type="submit" value="Enter" name="enter" />
            </form>
        </div>
    </body>
</html>
