package servlet;

import dao.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/OrderDetailServlet"})
public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            OrdersDAO ordersDAO = new OrdersDAO();
//            ordersDAO.getConnection();
            ordersDAO.initial();

            request.getRequestDispatcher("show_order.jsp").include(request, response); // double reload
            ResultSet resultSet = ordersDAO.selectDetail(Integer.parseInt(request.getParameter("id")));
            ordersDAO.closeConnection();
            // set data into list
            try {
                if (resultSet.next()) {
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
                    request.setAttribute("orderNumber", "訂單編號: " + resultSet.getInt("OrderID"));
                    request.getRequestDispatcher("show_order.jsp").forward(request, response);

                    resultSet.beforeFirst(); // do while
                    while (resultSet.next()) {
//                        out.print("<tr>");
//                        out.print("<td>" + resultSet.getInt("ID") + "</td>");
//                        out.print("<td>" + resultSet.getInt("ProductID") + "</td>");
//                        out.print("<td style=\"text-align: right\">" + resultSet.getDouble("Price") + " 元</td>");
//                        out.print("<td style=\"text-align: right\">" + resultSet.getInt("Number") + "</td>");
//                        out.print("<td style=\"text-align: right\">"
//                                + resultSet.getDouble("Price") * resultSet.getInt("Number") + " 元</td>");
//                        out.print("</tr>");
                    }
//                    out.print("</table>");
//                    out.print("</div>");
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrderDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(OrderDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
