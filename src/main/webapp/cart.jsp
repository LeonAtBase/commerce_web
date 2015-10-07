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
        <title>Cart Page</title>
        <style>
            a:hover{
                text-decoration: none;
            }
            th{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div style="margin-left:20px; margin-top: 20px">
            <table style="width: 40%">
                <tr>
                    <td><a href="show_product_jqgrid.jsp">繼續Shopping</a></td>
                    <td style="text-align: right">
                        <% out.print("使用者名稱: " + session.getAttribute("username"));%>
                    </td>
                </tr>
            </table>

            <table border="1" style="width: 40%">
                <tr style="background-color: lightblue; border-bottom-width: 0">
                    <th>商品編號</th>
                    <th>商品名稱</th>
                    <th>商品單價</th>
                    <th>購買數量</th>
                    <th>購買金額</th>
                    <th>移除項目</th>
                </tr>
                <script>
                    if (Number(sessionStorage.getItem("howManyItem")) === 0) {
                        document.write("<tr><td colspan='6' style=\"text-align: center\">購物車內沒有任何東西</td></tr>");
                    } else {
                        for (i = 1; i <= Number(sessionStorage.getItem("itemCount")); i++) {
                            if (sessionStorage.getItem("id" + i) !== null) {
                                document.write("<tr>");
                                document.write("<td>" + sessionStorage.getItem("id" + i) + "</td>");
                                document.write("<td>" + sessionStorage.getItem("name" + i) + "</td>");
                                document.write("<td style=\"text-align: right\">" + sessionStorage.getItem("price" + i) + " 元</td>");
                                document.write("<td style=\"text-align: right\">" + sessionStorage.getItem("number" + i) + "</td>");
                                document.write("<td style=\"text-align: right\">" + sessionStorage.getItem("total" + i) + " 元</td>");
                                document.write("<td style=\"text-align: center\"><input type='button' value='從購物車移除'" +
                                        "onclick=\"MyCheckIn(" +
                                        i + "," + // the item order in the cart
                                        sessionStorage.getItem("id" + i) + ",\'" +
                                        sessionStorage.getItem("name" + i) + "\'," +
                                        sessionStorage.getItem("price" + i) + "," +
                                        sessionStorage.getItem("number" + i) + "," +
                                        sessionStorage.getItem("total" + i) + ");\" /></td>");
                                document.write("</tr>");
                            }
                        }
                    }

                    MyCheckIn = function (orderInCart, rowId, rowName, rowPrice, rowNumber, rowTotal) {
                        // do Check In
                        window.alert(
                                '------ 您已將此項目移出購物車 ------' +
                                '\n商品編號: ' + rowId +
                                '\n商品名稱: ' + rowName +
                                '\n商品單價: ' + rowPrice + " 元" +
                                '\n購買數量: ' + rowNumber +
                                '\n購買金額: ' + rowTotal + " 元"
                                );
                        sessionStorage.removeItem("id" + orderInCart);
                        sessionStorage.removeItem("name" + orderInCart);
                        sessionStorage.removeItem("price" + orderInCart);
                        sessionStorage.removeItem("number" + orderInCart);
                        sessionStorage.removeItem("total" + orderInCart);
                        sessionStorage.removeItem("cart" + rowId); // remove check out record
                        sessionStorage.setItem("howManyItem", Number(sessionStorage.getItem("howManyItem")) - 1);
                        location.reload();
                    };
                </script>
            </table>
        </div>
    </body>
</html>
