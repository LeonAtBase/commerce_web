package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderDetail;
import model.Orders;

public class OrdersDAO {
    
    Connection connection = null;
    
    public Connection getConnection() {
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.0.54:3306/commerce", "guest", "123");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    public void insert(Orders orders, OrderDetail orderDetail) {
        ArrayList<Integer> productId = orderDetail.getProductId();
        ArrayList<Integer> number = orderDetail.getNumber();
        double amount = 0;
        try (PreparedStatement purchasedPreparedStatement = connection.prepareStatement("SELECT "
                + "* FROM product WHERE ID=?");
                PreparedStatement ordersStatement = connection.prepareStatement("INSERT INTO "
                        + "orders (CustomerID, Amount) VALUES (?, ?)")) {
            connection.setAutoCommit(false);
            for (int i = 0; i < productId.size(); i++) {
                purchasedPreparedStatement.setInt(1, productId.get(i));
                try (ResultSet purchasedResultSet = purchasedPreparedStatement.executeQuery()) {
                    while (purchasedResultSet.next()) {
                        amount = amount + purchasedResultSet.getDouble("Price") * number.get(i);
                    }
                }
            }
            ordersStatement.setInt(1, orders.getCustomerId());
            ordersStatement.setDouble(2, amount);
            ordersStatement.executeUpdate();
            // ---- orders complete ----
            int getLastInsertId = 0;
            try (Statement getLastInsertIdStatement = connection.createStatement();
                    ResultSet getLastInsertIdResultSet = getLastInsertIdStatement
                    .executeQuery("SELECT LAST_INSERT_ID()");
                    PreparedStatement orderDetailStatement = connection.prepareStatement("SELECT * "
                            + "FROM product WHERE ID=?")) {
                for (int i = 0; i < productId.size(); i++) {
                    orderDetailStatement.setInt(1, productId.get(i));
                    try (ResultSet orderDetailResultSet = orderDetailStatement.executeQuery();
                            PreparedStatement insertOrderDetailStatement = connection.prepareStatement(
                                    "INSERT INTO order_detail (OrderID, ProductID, Price, Number) "
                                    + "VALUES (?, ?, ?, ?)");) {
                        while (getLastInsertIdResultSet.next()) {
                            getLastInsertId = getLastInsertIdResultSet.getInt(1);
                        }
                        while (orderDetailResultSet.next()) {
                            insertOrderDetailStatement.setInt(1, getLastInsertId);
                            insertOrderDetailStatement.setInt(2, orderDetailResultSet.getInt("ID"));
                            insertOrderDetailStatement.setDouble(3, orderDetailResultSet.getDouble("Price"));
                            insertOrderDetailStatement.setInt(4, number.get(i));
                            insertOrderDetailStatement.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void selectAll() {
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {
            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID"));
                orders.setCustomerId(resultSet.getInt("CustomerID"));
                orders.setAmount(resultSet.getDouble("Amount"));
                orders.setBuyDateTime(resultSet.getTimestamp("BuyDateTime"));
                System.out.println(orders.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void select(Orders orders) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "orders WHERE ID=?");) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, orders.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders = new Orders();
                    orders.setId(resultSet.getInt("ID"));
                    orders.setCustomerId(resultSet.getInt("CustomerID"));
                    orders.setAmount(resultSet.getDouble("Amount"));
                    orders.setBuyDateTime(resultSet.getTimestamp("BuyDateTime"));
                    System.out.println(orders.toString());
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Orders orders) {
        try (PreparedStatement ordersStatement = connection.prepareStatement("DELETE FROM "
                + "orders WHERE ID=?");
                PreparedStatement orderDetailStatement = connection.prepareStatement("DELETE FROM "
                        + "order_detail WHERE OrderID=?")) {
            connection.setAutoCommit(false);
            ordersStatement.setInt(1, orders.getId());
            orderDetailStatement.setInt(1, orders.getId());
            ordersStatement.executeUpdate();
            orderDetailStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
