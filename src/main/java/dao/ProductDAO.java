package dao;

import data_source.DSF;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import model.Product;

public class ProductDAO {

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

//    public Connection getConnection() {
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://192.168.0.54:3306/commerce", "guest", "123");
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return connection;
//    }
    public void insert(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "
                + "product (Name, Price) VALUES (?, ?)");) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
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

    public void selectAll() {
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM product")) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("ID"));
                product.setName(resultSet.getString("Name"));
                product.setPrice(resultSet.getDouble("Price"));
                System.out.println(product.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void select(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "product WHERE ID=?");) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, product.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getInt("ID"));
                    product.setName(resultSet.getString("Name"));
                    product.setPrice(resultSet.getDouble("Price"));
                    System.out.println(product.toString());
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

    public void update(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product "
                + "SET Name=?, Price=? WHERE ID=?");) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
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

    public void delete(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM "
                + "product WHERE ID=?")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
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
            Logger.getLogger(CustomerDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
