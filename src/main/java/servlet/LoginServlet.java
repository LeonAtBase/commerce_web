package servlet;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.isEmpty()) {
                response.sendRedirect("login.jsp");
            } else {
                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.getConnection();
                out.print(customerDAO.validate(username));
                if (username.equals("guest") && password.equals("123")) {
                    out.print("<h1>Welcome " + username + "</h1>");
                    request.getRequestDispatcher("show_product.jsp").include(request, response);
                } else {
                    request.getRequestDispatcher("login.jsp").include(request, response);
                    out.print("<h2 style='color:red'>帳號或密碼錯誤，請重新輸入!</h2>");
                }
                customerDAO.closeConnection();
            }
        }
    }
}
