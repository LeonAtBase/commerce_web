<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap-ui.cssjquery-ui-1.8.19.custom.css" >
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid-bootstrap.css" >
        <link rel="stylesheet" type="text/css" href="Guriddo_jqGrid_JS_5.0.0/css/ui.jqgrid.css" >
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> 
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="Guriddo_jqGrid_JS_5.0.0/js/i18n/grid.locale-en.js"></script>
        <script>
            $.jgrid.defaults.width = 600;
        </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <meta charset="utf-8" />
        <title>Product Page</title>
    </head>
    <body>
        <div style="margin-left:20px">
            <table id="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {

                $("#jqGrid").jqGrid({
                    url: 'JqGridShowProductServlet',
                    mtype: "GET",
                    styleUI: 'Bootstrap',
                    datatype: "json",
                    colModel: [
                        {label: '商品編號', name: 'id', key: true, width: 75, sortable: true},
                        {label: '商品名稱', name: 'name', width: 150},
                        {label: '商品價格', name: 'price', width: 75, sortable: true},
                        {label: '購買數量', name: 'number', width: 75},
                    ],
                    caption: '產品清單',
                    cmTemplate: {sortable: false},
                    viewrecords: true,
                    height: 'auto',
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    pager: "#jqGridPager"
                });
            });
        </script>
    </body>
</html>