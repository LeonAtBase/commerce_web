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
            <form action="CheckServlet" method="get"> 
                <table style="width: 40%">
                    <tr>
                        <td><a href="show_product_jqgrid.jsp">繼續Shopping</a></td>
                        <td style="text-align: right">
                            <input type="hidden" name="how_many_item" id="how_many_item">
                            <input type="hidden" name="item_count" id="item_count">
                            <input type="hidden" name="total_amount" id="total_amount">
                            <script>
                                // 設購物車內商品數目，檢查是否有商品在購物車內
                                document.getElementById('how_many_item').value = sessionStorage.getItem("howManyItem");
                                // 目前到第幾個項目
                                document.getElementById('item_count').value = sessionStorage.getItem("itemCount");
                            </script>
                            <input type="submit" value="結帳" style="color: red">
                            <% out.print(" ∥ 使用者名稱: " + session.getAttribute("username"));%>
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
                            var totalAmount = 0;
                            for (i = 1; i <= Number(sessionStorage.getItem("itemCount")); i++) {
                                if (sessionStorage.getItem("id" + i) !== null) {
                                    totalAmount = totalAmount + Number(sessionStorage.getItem("total" + i));
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
                                    // set input value for CheckServlet insert into db
                                    // set id value
                                    document.write("<input type=\"hidden\" id=\"id\">");
                                    document.getElementById('id').id = "id" + i;
                                    document.getElementById('id' + i).name = "id" + i;
                                    document.getElementById('id' + i).value = sessionStorage.getItem("id" + i);
                                    // set price value
                                    document.write("<input type=\"hidden\" id=\"price\">");
                                    document.getElementById('price').id = "price" + i;
                                    document.getElementById('price' + i).name = "price" + i;
                                    document.getElementById('price' + i).value = sessionStorage.getItem("price" + i);
                                    // set number value
                                    document.write("<input type=\"hidden\" id=\"number\">");
                                    document.getElementById('number').id = "number" + i;
                                    document.getElementById('number' + i).name = "number" + i;
                                    document.getElementById('number' + i).value = sessionStorage.getItem("number" + i);
                                }
                            }
                            document.write("<tr><td colspan='6' style='text-align: center'>購物車目前購買總金額: " + totalAmount + " 元</td></tr>");
                            // 設購物車商品總金額，結帳新增至DB
                            document.getElementById('total_amount').value = totalAmount;
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
            </form>
        </div>
    </body>
</html>
