<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="LoginServlet" method="post" >
            <h2>購物商城登入系統</h2>
            Username:<br>
            <input type="text" name="username" size="15">
            <br>
            Password:<br>
            <input type="password" name="password" size="15">
            <br><br>
            <input type="submit" value="Login">
        </form>
    </body>
</html>
