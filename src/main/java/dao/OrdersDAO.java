package dao;

import com.sun.rowset.CachedRowSetImpl;
import data_source.DSF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import model.OrderDetail;
import model.Orders;
import model.ShowOrderDetail;

public class OrdersDAO {

    DataSource dataSource = null;
    Connection connection = null;

    public Connection getConnection() {
        dataSource = DSF.getDataSource();
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public void initial() {
        dataSource = DSF.getDataSource();
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Connection connection = null;
//
//    public Connection getConnection() {
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://192.168.0.54:3306/commerce", "guest", "123");
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return connection;
//    }
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

    public void insertCartToDB(Orders orders, OrderDetail orderDetail) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders "
                + "(CustomerID, Amount) VALUES (?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, orders.getCustomerId());
            preparedStatement.setDouble(2, orders.getAmount());
            preparedStatement.executeUpdate();
            // ---- orders complete ----

            int getLastInsertId = 0;
            ArrayList<Integer> productId = orderDetail.getProductId();
            ArrayList<Integer> number = orderDetail.getNumber();
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

    public int findIdByName(String username) {
        int id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "customer Where Name=?")) { // rewrite SELECT * to SELECT id (performance)
            connection.setAutoCommit(false);
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt("ID");
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
        return id;
    }

    public CachedRowSet selectDetail(int orderId) {
        CachedRowSet cachedRowSet = null; // off-line working
        try {
            cachedRowSet = new CachedRowSetImpl();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "order_detail WHERE OrderID=?")) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                cachedRowSet.populate(resultSet);
            }
//            // for testing
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    System.out.print("ID: " + resultSet.getInt("ID"));
//                    System.out.print("\tPID: " + resultSet.getInt("ProductID"));
//                    System.out.print("\tPrice: " + resultSet.getDouble("Price"));
//                    System.out.print("\tNumber: " + resultSet.getInt("Number"));
//                    System.out.println("\tAmount: " + resultSet.getDouble("Price")
//                            * resultSet.getInt("Number"));
//                }
//            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cachedRowSet;
    }

    public List<ShowOrderDetail> selectDetailByOrderId(int orderId) {
        List<ShowOrderDetail> showOrderDetails = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "order_detail WHERE OrderID=?")) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ShowOrderDetail showOrderDetail = new ShowOrderDetail();
                    showOrderDetail.setId(resultSet.getInt("ID"));
                    showOrderDetail.setProductId(resultSet.getInt("ProductID"));
                    showOrderDetail.setPrice(resultSet.getDouble("Price"));
                    showOrderDetail.setNumber(resultSet.getInt("Number"));
                    showOrderDetail.setTotal(resultSet.getDouble("Price") * resultSet.getInt("Number"));
                    showOrderDetails.add(showOrderDetail);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return showOrderDetails;
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
