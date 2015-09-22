package test;

import dao.ProductDAO;
import model.Product;

public class ProductTest {

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.getConnection();
        
//        // 新增一筆產品資料
//        Product product = new Product();
//        product.setName("茶裏王台式綠茶");
//        product.setPrice(16);
//        productDAO.insert(product);
        
        // 查詢所有產品資料
        productDAO.selectAll();
        
//        // 查詢一筆產品資料
//        Product product = new Product();
//        product.setId(3); // 請輸入欲查詢產品id
//        productDAO.select(product);
        
//        // 修改產品資料
//        Product product = new Product();
//        product.setId(2); // 請輸入欲修改產品id
//        product.setName("左岸咖啡館");
//        product.setPrice(33);
//        productDAO.update(product);
        
//        // 刪除一筆產品資料
//        Product product = new Product();
//        product.setId(2); // 請輸入欲刪除產品id
//        productDAO.delete(product);
        
        productDAO.closeConnection();
    }
}
