<%@page import="dao.OrdersDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" media="screen" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap-ui.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> 
        <script src="Guriddo_jqGrid_JS_5.0.0/js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="Guriddo_jqGrid_JS_5.0.0/js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <title>Order Page</title>
        <style>
            a{
                font-size: 18px;
                border-style: double;
            }
            a:hover{
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <%  int findIdByName = 0;
            OrdersDAO ordersDAO = new OrdersDAO();
            ordersDAO.getConnection();
            findIdByName = ordersDAO.findIdByName(session.getAttribute("username").toString());
            ordersDAO.closeConnection();
            session.setAttribute("id", findIdByName);
        %>
        <sql:setDataSource var="datasource" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://192.168.0.54/commerce"
                           user="guest"  password="123"/>
        <sql:query var="rs" dataSource="${datasource}">
            SELECT * FROM orders WHERE CustomerID=${sessionScope.id};
        </sql:query>

        <table style="width: 40%">
            <tr>
                <td><a href="show_product.jsp">返回購買頁面</a></td>
                <td style="text-align: right">
                    <% out.print("使用者名稱: " + session.getAttribute("username"));%>
                </td>
            </tr>
        </table>

        <table border="5" style="width: 40%">
            <tr style="background-color: lightblue">
                <th>訂單編號</th>
                <th>消費金額</th>
                <th>消費時間</th>
            </tr>
            <c:forEach var="row" items="${rs.rows}">
                <tr>
                    <td><c:out value="${row.id}"/></td>
                    <td style="text-align: right">
                        <c:out value="${row.amount}"/>
                    </td >
                    <td style="text-align: center">
                        <c:out value="${row.buydatetime}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
