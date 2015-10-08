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
        <title>Product Page</title>
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
        <sql:setDataSource var="datasource" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://192.168.0.137/commerce"
                           user="guest"  password="123"/>
        <sql:query dataSource="${datasource}" var="rs">
            SELECT * FROM product;
        </sql:query>

        <table style="width: 40%">
            <tr>
                <td><a href="show_order.jsp">查詢購買紀錄</a></td>
                <td style="text-align: right">
                    <% out.print("使用者名稱: " + session.getAttribute("username"));%>
                </td>
            </tr>
        </table>



        <form action="CheckNumericServlet" method="get">
            <table border="5" style="width: 40%">
                <tr style="background-color: lightblue">
                    <th>商品編號</th>
                    <th>商品名稱</th>
                    <th>商品價格</th>
                    <th>購買數量</th>
                </tr>
                <c:forEach var="row" items="${rs.rows}">
                    <c:set var="lastId" value="${row.id}"/>
                    <tr>
                        <td><c:out value="${row.id}"/></td>
                        <td><c:out value="${row.name}"/></td>
                        <td style="text-align: right">
                            <c:out value="${row.price}"/>
                            <input type="hidden" name="price${row.id}" value="${row.price}">
                        </td>                  
                        <td>
                            <input type="text" name="number${row.id}" size="3" style="text-align: right" value="0">
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4" style="text-align: right; border-width: 0">
                        <input type="hidden" name="last_id" value="${lastId}">
                        <input type="reset" value="數量歸零">
                        <input type="submit" value="確定購買">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
