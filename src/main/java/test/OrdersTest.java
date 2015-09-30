package test;

import dao.OrdersDAO;
import java.util.ArrayList;
import model.OrderDetail;
import model.Orders;

public class OrdersTest {

    public static void main(String[] args) {
        OrdersDAO ordersDAO = new OrdersDAO();
        ordersDAO.getConnection();
        
//        // 測試
//        ArrayList test = new ArrayList();
//        test.add(5);
//        test.add(8);
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setProductId(test);
//        System.out.println(orderDetail.getProductId().get(0));

//        // 新增一筆訂單
//        Orders orders = new Orders();
//        OrderDetail orderDetail = new OrderDetail();
//        orders.setCustomerId(2); // 會員下訂單
//        ArrayList productId = new ArrayList(); // 下商品id
//        ArrayList number = new ArrayList(); // 對應的商品數量
//        productId.add(1);
//        number.add(1);
//        productId.add(4);
//        number.add(2);
//        orderDetail.setProductId(productId);
//        orderDetail.setNumber(number);
//        ordersDAO.insert(orders, orderDetail);
        
//        //查詢所有訂單資料
//        ordersDAO.selectAll();
        
//        // 查詢一筆訂單資料
//        Orders orders = new Orders();
//        orders.setId(24); // 請輸入欲查詢訂單id
//        ordersDAO.select(orders);
        
//        // 刪除一筆訂單
//        Orders orders = new Orders();
//        orders.setId(24); // 請輸入欲刪除訂單id
//        ordersDAO.delete(orders);
        
        // 查詢一筆訂單明細
        int orderId = 25;
        ordersDAO.selectDetail(orderId);

        ordersDAO.closeConnection();
    }
}
