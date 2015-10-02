package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ProductDAO;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ShowProduct;

@WebServlet(name = "JqGridShowProductServlet", urlPatterns = {"/JqGridShowProductServlet"})
public class JqGridShowProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        String sord = request.getParameter("sord");
        String sidx = request.getParameter("sidx");

        int page = Integer.parseInt(request.getParameter("page")); // 目前頁數
        int pageSize = Integer.parseInt(request.getParameter("rows")); // 一頁筆數
        int rowCount = 0; // 總筆數
        int pageCount = 0; // 總共頁數
        List<ShowProduct> showProducts = new ArrayList<>();

        ProductDAO productDAO = new ProductDAO();
        productDAO.getConnection();
        rowCount = productDAO.getTableRowCount(); // 總筆數取得
        pageCount = rowCount / pageSize + 1; // 計算總共頁數
        ResultSet resultSet = productDAO.showProduct(sidx, sord, (page - 1) * pageSize, pageSize);
        try {
            while (resultSet.next()) {
                ShowProduct showProduct = new ShowProduct();
                showProduct.setId(resultSet.getInt("ID"));
                showProduct.setName(resultSet.getString("Name"));
                showProduct.setPrice(resultSet.getDouble("Price"));
                showProducts.add(showProduct);
            }

//            if (sord != null && sord.length() > 0) {
//                if (("id").equals(sidx) && ("desc").equals(sord)) {
//                    Collections.reverse(showProducts);
//                }
//                if (("price").equals(sidx)) {
//                    ResultSet rsOrderByPrice = productDAO.showProductOrderByPrice((page - 1) * pageSize, pageSize);
//                    while (rsOrderByPrice.next()) {
//                        ShowProduct showProduct = new ShowProduct();
//                        showProduct.setId(rsOrderByPrice.getInt("ID"));
//                        showProduct.setName(rsOrderByPrice.getString("Name"));
//                        showProduct.setPrice(rsOrderByPrice.getDouble("Price"));
//                        showProducts.add(showProduct);
//                    }
//                    if (("desc").equals(sord)) {
//                        Collections.reverse(showProducts);
//                    }
//                }
//            }
        } catch (SQLException ex) {
            Logger.getLogger(JqGridShowProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonArray = gson.toJson(showProducts);
        jsonArray = "{\"page\":" + page + ",\"total\":\"" + pageCount + "\",\"records\":" + rowCount
                + ",\"rows\":" + jsonArray + "}";

        System.out.println(jsonArray);
        response.getWriter().print(jsonArray);
    }
}
