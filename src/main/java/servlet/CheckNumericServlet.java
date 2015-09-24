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
import model.OrderDetail;
import model.Orders;

@WebServlet(name = "CheckNumericServlet", urlPatterns = {"/CheckNumericServlet"})
public class CheckNumericServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // 檢查輸入資料是否為數字
            boolean status = true;
            int countNumber = 0;
            for (int j = 1; j <= Integer.parseInt(request.getParameter("last_id")) && status; j++) {
                if (request.getParameter("number" + j) != null) {
                    char[] checkNumeric = request.getParameter("number" + j).toCharArray();
                    for (int i = 0; i < checkNumeric.length; i++) {
                        if (!Character.isDigit(checkNumeric[i])) {
                            out.print("<h1 style='color:red'>數量欄位請輸入數字</h1>");
                            request.getRequestDispatcher("show_product.jsp").include(request, response);
                            status = false;
                            break;
                        }
                    }
                    countNumber = countNumber + Integer.parseInt(request.getParameter("number" + j));
                }
            }

            // 檢查是否有輸入數字
            if (countNumber == 0) {
                out.print("<h1 style='color:red'>您尚未輸入任何數字</h1>");
                request.getRequestDispatcher("show_product.jsp").include(request, response);
            } else {

                // 資料驗證皆沒問題，開始新增訂單至db
                OrdersDAO ordersDAO = new OrdersDAO();
                ordersDAO.getConnection();
                // 新增一筆訂單
                Orders orders = new Orders();
                OrderDetail orderDetail = new OrderDetail();
                orders.setCustomerId(2); // 會員下訂單
                ArrayList<Integer> productId = new ArrayList(); // 下商品id
                ArrayList<Integer> number = new ArrayList(); // 對應的商品數量
                double total = 0;
                
                for (int i = 1; i <= Integer.parseInt(request.getParameter("last_id")); i++) {
                    if (request.getParameter("number" + i) != null) {
                        if (Integer.parseInt(request.getParameter("number" + i)) != 0) {
                            productId.add(i);
                            number.add(Integer.parseInt(request.getParameter("number" + i)));
                            total = total + Double.parseDouble(request.getParameter("price" + i))
                                    * Double.parseDouble(request.getParameter("number" + i));
                        }
                    }
                }
                out.println("<h1 style='color:red'>成功新增一筆訂單</h1>");
                out.print("<h1>您這次購買的總金額為： " + total + "元</h1>");
                request.getRequestDispatcher("show_product.jsp").include(request, response);

                orderDetail.setProductId(productId);
                orderDetail.setNumber(number);
                ordersDAO.insert(orders, orderDetail);

                ordersDAO.closeConnection();
            }
        }
    }
}
