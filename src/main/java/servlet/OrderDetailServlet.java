package servlet;

import dao.OrdersDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ShowOrderDetail;

@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/OrderDetailServlet"})
public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {

        OrdersDAO ordersDAO = new OrdersDAO();
        ordersDAO.initial();
//        request.getRequestDispatcher("show_order.jsp").include(request, response); // double reload
//        ResultSet resultSet = ordersDAO.selectDetail(Integer.parseInt(request.getParameter("id")));

        // put order detail data into array list that replace putting them into result set, that would be better
        List<ShowOrderDetail> showOrderDetails = new ArrayList<>();
        showOrderDetails = ordersDAO.selectDetailByOrderId(Integer.parseInt(request.getParameter("id")));
        ordersDAO.closeConnection();

        if (showOrderDetails.isEmpty()) {
            request.getRequestDispatcher("show_order.jsp").forward(request, response);
        } else {
            // set javascript style display to block replace none if the order detail is not empty
            request.setAttribute("displayOrderDetailTable", "document.getElementById('order-detail').style.display = 'block';");
            request.setAttribute("orderNumber", request.getParameter("id"));
            request.setAttribute("showOrderDetails", showOrderDetails);
            request.getRequestDispatcher("show_order.jsp").forward(request, response);
        }
//        try {
//            if (resultSet.next()) {
//                // set javascript style display to block replace none if the order detail is not empty
//                request.setAttribute("displayOrderDetailTable", "document.getElementById('order-detail').style.display = 'block';");
//
//                request.setAttribute("orderNumber", "訂單編號: " + resultSet.getInt("OrderID"));
//                request.setAttribute("headingOrderDetailId", "消費編號");
//                request.setAttribute("headingOrderDetailProductId", "商品編號");
//                request.setAttribute("headingOrderDetailPrice", "單品價格");
//                request.setAttribute("headingOrderDetailNumber", "購買數量");
//                request.setAttribute("headingOrderDetailTotal", "單項總金額");
//                    out.print("<br>");
//                    out.print("<div style=\"margin-left:20px\">");
//                    out.print("<table border=\"1\" style=\"border-bottom-width: 0\">");
//                    out.print("<tr style=\"background-color: pink\">");
//                    out.print("<th>訂單編號: " + resultSet.getInt("OrderID") + "</th>");
//                    out.print("</tr>");
//                    out.print("</table>");
//
//                    out.print("<table border=\"1\" style=\"width: 550px\">");
//                    out.print("<tr style=\"background-color: lightblue\">");
//                    out.print("<th>消費編號</th>");
//                    out.print("<th>商品編號</th>");
//                    out.print("<th>單品價格</th>");
//                    out.print("<th>購買數量</th>");
//                    out.print("<th>單項總金額</th>");
//                    out.print("</tr>");
//                resultSet.beforeFirst(); // do while
//                List<ShowOrderDetail> showOrderDetails = new ArrayList<>();
//                while (resultSet.next()) {
//                    ShowOrderDetail showOrderDetail = new ShowOrderDetail();
//                    showOrderDetail.setId(resultSet.getInt("ID"));
//                    showOrderDetail.setProductId(resultSet.getInt("ProductID"));
//                    showOrderDetail.setPrice(resultSet.getDouble("Price"));
//                    showOrderDetail.setNumber(resultSet.getInt("Number"));
//                    showOrderDetail.setTotal(resultSet.getDouble("Price") * resultSet.getInt("Number"));
//                    showOrderDetails.add(showOrderDetail);
//                        out.print("<tr>");
//                        out.print("<td>" + resultSet.getInt("ID") + "</td>");
//                        out.print("<td>" + resultSet.getInt("ProductID") + "</td>");
//                        out.print("<td style=\"text-align: right\">" + resultSet.getDouble("Price") + " 元</td>");
//                        out.print("<td style=\"text-align: right\">" + resultSet.getInt("Number") + "</td>");
//                        out.print("<td style=\"text-align: right\">"
//                                + resultSet.getDouble("Price") * resultSet.getInt("Number") + " 元</td>");
//                        out.print("</tr>");
//                }
//                request.setAttribute("showOrderDetails", showOrderDetails);
//                request.getRequestDispatcher("show_order.jsp").forward(request, response);
//                    out.print("</table>");
//                    out.print("</div>");
//            } else {
//                request.getRequestDispatcher("show_order.jsp").forward(request, response);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(OrderDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (IOException ex) {
//            Logger.getLogger(OrderDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
