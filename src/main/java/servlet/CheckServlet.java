package servlet;

import dao.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.OrderDetail;
import model.Orders;

@WebServlet(name = "CheckServlet", urlPatterns = {"/CheckServlet"})
public class CheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // examine if the shopping cart is empty
            if (Integer.parseInt(request.getParameter("how_many_item")) == 0) {
                request.getRequestDispatcher("cart.jsp").include(request, response);
                out.print("<script>alert('購物車沒任何東西');</script>");
            } else {
                // the shopping cart is not empty then insert into db
                OrdersDAO ordersDAO = new OrdersDAO();
                ordersDAO.getConnection();
                // 新增一筆訂單
                Orders orders = new Orders();
                OrderDetail orderDetail = new OrderDetail();
                // 用username找id
                HttpSession session = request.getSession();
                int findIdByName = 0;
                findIdByName = ordersDAO.findIdByName(session.getAttribute("username").toString());
                // insert into orders
                orders.setCustomerId(findIdByName); // 會員下訂單
                orders.setAmount(Double.parseDouble(request.getParameter("total_amount"))); // 此訂單總金額
                // insert into order_detail
                ArrayList<Integer> productId = new ArrayList(); // 下商品id
                ArrayList<Integer> number = new ArrayList(); // 對應的商品數量
                for (int i = 1; i <= Integer.parseInt(request.getParameter("item_count")); i++) {
                    // if item move out from cart
                    if (request.getParameter("id" + i) != null) {
                        productId.add(Integer.parseInt(request.getParameter("id" + i)));
                        number.add(Integer.parseInt(request.getParameter("number" + i)));
                    }
                }
                orderDetail.setProductId(productId);
                orderDetail.setNumber(number);

                ordersDAO.insertCartToDB(orders, orderDetail);
                ordersDAO.closeConnection();

                request.getRequestDispatcher("show_product_jqgrid.jsp").include(request, response);
                out.print("<script>alert('訂單新增成功');</script>");
                out.print("<script>sessionStorage.clear();</script>");
            }
        }
    }
}
