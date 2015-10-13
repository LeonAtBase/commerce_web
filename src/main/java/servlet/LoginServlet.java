package servlet;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            if (account.isEmpty() || password.isEmpty()) {
//                response.sendRedirect("login.jsp");
                request.setAttribute("warning", "尚未輸入帳號或密碼!");
                request.getRequestDispatcher("login.jsp").include(request, response);
            } else {
                CustomerDAO customerDAO = new CustomerDAO();
//                customerDAO.getConnection(); // if no return, rename method
                customerDAO.initial();
                if (customerDAO.validate(account, password)) {
                    String username = customerDAO.findNameByMail(account);
                    int userId = customerDAO.findIdByMail(account);
                    customerDAO.closeConnection();
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("id", userId);
//                    out.print("<h1>Welcome " + session.getAttribute("username") + "</h1>"); // not mvc, rewrite by EL
                    request.getRequestDispatcher("show_product_jqgrid.jsp").include(request, response);
//                    request.getRequestDispatcher("show_product.jsp").include(request, response);
                } else {
                    customerDAO.closeConnection();
                    request.setAttribute("warning", "帳號或密碼錯誤，請重新輸入!");
                    request.getRequestDispatcher("login.jsp").include(request, response);
//                    out.print("<h2 style='color:red'>帳號或密碼錯誤，請重新輸入!</h2>"); // not mvc
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
