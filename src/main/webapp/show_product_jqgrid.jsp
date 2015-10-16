<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap-ui.css">
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap.css">
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> 
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/i18n/grid.locale-en.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script>
            $.jgrid.defaults.width = 500;</script>
        <style>
            div.product {
                margin-left: 20px;
                margin-top: 20px;
            }
            div.product table.nav {
                width: 500px;
            }
            div.product table.nav a:hover {
                text-decoration: none;
            }
            td.align-right {
                text-align: right;
            }

            // adjust jqGrid heading
            .ui-jqgrid .ui-jqgrid-htable th div {
                height: auto;
                overflow: hidden;
                padding-right: 4px;
                padding-top: 2px;
                position: relative;
                vertical-align: text-top;
                white-space: normal !important;
            }
        </style>
        <title>Product Page</title>
    </head>
    <body>
        <div class="product">
            <table class="nav">
                <tr>
                    <td><a href="show_order.jsp">查詢購買紀錄</a></td>
                    <td class="align-right">
                        <a href="cart.jsp">我的購物車</a>
                        ∥ 使用者名稱: ${sessionScope.username}
                    </td>
                </tr>
            </table>

            <table id="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {

                var lastSelect;
                // what is the page right now
                var page;

                $("#jqGrid").jqGrid({
                    url: 'JqGridShowProductServlet',
                    mtype: 'GET',
                    styleUI: 'Bootstrap',
                    datatype: 'json',
                    colModel: [
                        {label: '商品編號', name: 'id', width: 75, key: true, sortable: true},
                        {label: '商品名稱', name: 'name', width: 150},
                        {label: '商品價格', name: 'price', width: 75, sortable: true, align: 'right'},
                        {label: '購買數量', name: 'number', width: 75, align: 'right',
                            editable: true, edittype: 'select', editoptions: {value: "1:1;2:2;3:3;4:4;5:5;6:6;7:7;8:8;9:9"}},
                        {label: '購物車', name: 'cart', width: 80, align: 'center'}
                    ],
                    caption: '產品清單',
                    cmTemplate: {sortable: false},
                    height: 'auto',
                    pager: "#jqGridPager",
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    viewrecords: true,
                    // 畫面載入
                    gridComplete: function () {
                        // 每筆商品載入input button
                        var ids = jQuery("#jqGrid").jqGrid('getDataIDs');
                        for (var i = 0; i < ids.length; i++) {
                            var rowId = ids[i];
                            var rowName = jQuery('#jqGrid').jqGrid('getCell', rowId, 'name');
                            var rowPrice = jQuery('#jqGrid').jqGrid('getCell', rowId, 'price');
                            if (sessionStorage.getItem("cart" + rowId) === rowId) {
                                var checkIn = "<input style='height:25px; color:brown' type='button' value='從購物車移除'" +
                                        "onclick=\"MyCheckIn(" +
                                        rowId + ",\'" + rowName + "\'," + rowPrice + ");\" />";
                                jQuery("#jqGrid").jqGrid('setRowData', rowId, {cart: checkIn});
                            } else {
                                var checkout = "<input style='height:25px' type='button' value='放入購物車'" +
                                        "onclick=\"MyCheckOut(" +
                                        rowId + ",\'" + rowName + "\'," + rowPrice + ");\" />";
                                jQuery("#jqGrid").jqGrid('setRowData', ids[i], {cart: checkout});
                            }
                        }
                    },
                    // select下拉選單setup
                    onSelectRow: function (id) {
                        // 是否作用同個下拉選單
                        if (id && id !== lastSelect) {
                            // 將最後一次下拉選單恢復原狀
                            jQuery(this).jqGrid('restoreRow', lastSelect);
                            lastSelect = id;
                        }
                        jQuery(this).editRow(id, true);
                    },
                    // something it will do when paging
                    onPaging: function (pgButton) {
                        page = jQuery('#jqGrid').jqGrid('getGridParam', 'page');
                        switch (pgButton) {
                            case 'next':
                                page = page + 1;
                                break;
                            case 'prev':
                                page = page - 1;
                                break;
                            case 'last':
                                page = jQuery('#jqGrid').jqGrid('getGridParam', 'lastpage');
                                break;
                            case 'first':
                                page = 1;
                                break;
                            case 'user':
                                page = $(".ui-pg-input").val();
                                break;
                        }
                    },
                    // something it will do when sorting some column
                    onSortCol: function () {
                        // if doesn't have this line, the page parameter will always be 1 when sorting column
                        jQuery('#jqGrid').jqGrid('setGridParam', {page: page});
                    }
                });
                MyCheckOut = function (rowId, rowName, rowPrice) {
                    // do Check Out
                    var rowNumber = $('#jqGrid option:selected').text(); // 取得下拉選單選取的數字
                    var grid = $("#jqGrid");
                    var rowKey = grid.getGridParam("selrow"); // 取得選取的row id
                    if (rowNumber !== "" && Number(rowKey) === rowId) { // 有下拉選取數字，且選取中的row與按鈕為同一筆
                        window.alert(
                                '------ 您放入購物車的商品 ------' +
                                '\n商品編號: ' + rowId +
                                '\n商品名稱: ' + rowName +
                                '\n商品單價: ' + rowPrice + " 元" +
                                '\n購買數量: ' + rowNumber +
                                '\n購買金額: ' + rowPrice * rowNumber + " 元"
                                );
                        var itemCount = sessionStorage.getItem("itemCount");
                        itemCount = Number(itemCount) + 1;
                        sessionStorage.setItem("id" + itemCount, rowId);
                        sessionStorage.setItem("name" + itemCount, rowName);
                        sessionStorage.setItem("price" + itemCount, rowPrice);
                        sessionStorage.setItem("number" + itemCount, rowNumber);
                        sessionStorage.setItem("total" + itemCount, rowPrice * rowNumber);
                        sessionStorage.setItem("cart" + rowId, rowId); // set check out record
                        sessionStorage.setItem("itemCount", itemCount);
                        sessionStorage.setItem("howManyItem", Number(sessionStorage.getItem("howManyItem")) + 1);
                        // replace "Check Out" button to "Check In"
                        var checkIn = "<input style='height:25px; color:brown' type='button' value='從購物車移除'" +
                                "onclick=\"MyCheckIn(" +
                                rowId + ",\'" + rowName + "\'," + rowPrice + ");\" />";
                        jQuery("#jqGrid").jqGrid('setRowData', rowId, {cart: checkIn});
                    } else {
                        alert("尚未選取購買數量");
                    }
                };
                MyCheckIn = function (rowId, rowName, rowPrice) {
                    // do Check In

                    // 找出要移出購物車項目的第幾個順序
                    for (i = 1; i <= Number(sessionStorage.getItem("itemCount")); i++) {
                        if (rowId === Number(sessionStorage.getItem("id" + i))) {
                            var rowNumber = sessionStorage.getItem("number" + i);
                            var orderInCart = i;
                        }
                    }
                    window.alert(
                            '------ 您已將此項目移出購物車 ------' +
                            '\n商品編號: ' + rowId +
                            '\n商品名稱: ' + rowName +
                            '\n商品單價: ' + rowPrice + " 元" +
                            '\n購買數量: ' + rowNumber +
                            '\n購買金額: ' + rowPrice * rowNumber + " 元"
                            );
                    sessionStorage.removeItem("id" + orderInCart);
                    sessionStorage.removeItem("name" + orderInCart);
                    sessionStorage.removeItem("price" + orderInCart);
                    sessionStorage.removeItem("number" + orderInCart);
                    sessionStorage.removeItem("total" + orderInCart);
                    sessionStorage.removeItem("cart" + rowId); // remove check out record
                    sessionStorage.setItem("howManyItem", Number(sessionStorage.getItem("howManyItem")) - 1);
                    // replace "Check In" button to "Check Out" like in MyCheckOut 
                    var checkout = "<input style='height:25px' type='button' value='放入購物車'" +
                            "onclick=\"MyCheckOut(" +
                            rowId + ",\'" + rowName + "\'," + rowPrice + ");\" />";
                    jQuery("#jqGrid").jqGrid('setRowData', rowId, {cart: checkout});
                }
                ;
            });
        </script>
    </body>
</html>