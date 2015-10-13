<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <meta charset="UTF-8">
        <script>
//            sessionStorage.clear();
            sessionStorage.setItem("itemCount", 0);
            sessionStorage.setItem("howManyItem", 0);
        </script>
        <title>Login</title>
    </head>
    <body>
        <form action="LoginServlet" method="get" >
            <h2>購物商城登入系統</h2>
            <label>Account:</label><br>
            <input type="text" name="account" size="15" placeholder="輸入您的電子郵件">
            <br>
            <label>Password:</label><br>
            <input type="password" name="password" size="15" placeholder="密碼">
            <br><br>
            <input type="submit" value="Login">
        </form>
        <h2 style='color:red'>
            ${requestScope.warning}
        </h2>
    </body>
</html>