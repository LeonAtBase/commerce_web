<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" media="screen" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap-ui.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> 
        <script src="Guriddo_jqGrid_JS_5.0.0/js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="Guriddo_jqGrid_JS_5.0.0/js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <title>Order Page</title>
        <style>
            div.orders {
                margin-left: 20px;
                margin-top: 20px;
            }
            div.orders table.nav,
            div.orders table.section {
                width: 500px;
            }
            div.orders table.nav a:hover {
                text-decoration: none;
            }
            div.orders table.section,
            div.orders table.section th,
            div.orders table.section td {
                border: 1px solid;
            }
            div.orders table.section tr.table-heading {
                background-color: lightblue;
            }
            div.orders table.section tr.table-heading th,
            td.align-center {
                text-align: center;
            }
            td.align-right {
                text-align: right;
            }

            /*order detail table css setting*/
            div.order-detail {
                margin-left: 20px;
                display: none;
            }
            div.order-detail table.sheet,
            div.order-detail table.sheet th,
            div.order-detail table.section,
            div.order-detail table.section th,
            div.order-detail table.section td {
                border: 1px solid;
            }
            div.order-detail table.sheet,
            div.order-detail table.sheet th {
                border-bottom: 0;
            }
            div.order-detail table.sheet tr {
                background-color: pink;
            }
            div.order-detail table.section {
                width: 500px;
            }
            div.order-detail table.section tr.table-heading {
                background-color: lightblue;
            }
            div.order-detail table.section tr.table-heading th{
                text-align: center;
            }
            div.order-detail table.section td {
                height: 25px;
            }
        </style>
        <!-- it is better to use CSS by class or id -->
    </head>
    <body>
        <sql:setDataSource var="datasource" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://192.168.0.137/commerce"
                           user="guest"  password="123"/>
        <sql:query var="rs" dataSource="${datasource}">
            SELECT * FROM orders WHERE CustomerID=${sessionScope.id}; 
        </sql:query>

        <div class="orders">    
            <table class="nav">
                <tr>
                    <td><a href="show_product_jqgrid.jsp">返回購買頁面</a></td>
                    <!--<td><a href="show_product.jsp">返回購買頁面</a></td>-->
                    <td class="align-right">
                        <!-- rewrite to EL -->
                        <% // out.print("使用者名稱: " + session.getAttribute("username"));%>
                        使用者名稱: ${sessionScope.username}
                    </td>
                </tr>
            </table>

            <table class="section">
                <tr class="table-heading">
                    <th>訂單編號</th>
                    <th>消費金額</th>
                    <th>消費時間</th>
                    <th>消費明細</th>
                </tr>
                <c:forEach var="row" items="${rs.rows}">
                    <tr>
                        <td><c:out value="${row.id}"/></td>
                        <td class="align-right">
                            <c:out value="${row.amount} 元"/>
                        </td>
                        <td class="align-center">
                            <c:out value="${row.buydatetime}"/>
                        </td>
                        <td class="align-center">
                            <form action="OrderDetailServlet" method="get">
                                <input type="submit" value="查詢明細">
                                <input type="hidden" name="id" value="${row.id}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!--order detail table display-->            
        <br>
        <div class="order-detail" id="order-detail">
            <!--get javascript style display to block replace none if the order detail is not empty-->
            <script>${requestScope.displayOrderDetailTable}</script>
            <table class="sheet">
                <tr>
                    <th>訂單編號: ${requestScope.orderNumber}</th>
                </tr>
            </table>

            <table class="section">
                <tr class="table-heading">
                    <th>消費編號</th>
                    <th>商品編號</th>
                    <th>單品價格</th>
                    <th>購買數量</th>
                    <th>單項總金額</th>
                </tr>
                <c:forEach var="row" items="${requestScope.showOrderDetails}">
                    <tr>
                        <td><c:out value="${row.id}"/></td>
                        <td><c:out value="${row.productId}"/></td>
                        <td class="align-right"><c:out value="${row.price}"/> 元</td>
                        <td class="align-right"><c:out value="${row.number}"/></td>
                        <td class="align-right"><c:out value="${row.total}"/> 元</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>