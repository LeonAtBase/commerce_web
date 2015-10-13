package dao;

import data_source.DSF;
import data_source.DataSourceFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import model.Customer;

public class CustomerDAO {

    DataSource dataSource = null;
    Connection connection = null;

    public Connection getConnection() { // should not be return
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

//    DataSource ds = DataSourceFactory.getMySQLDataSource();
//    Connection connection = null;
//
//    public Connection getConnection() {
//        try {
//            connection = ds.getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return connection;
//    }
//    public Connection getConnection() {
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://192.168.0.54:3306/commerce", "guest", "123");
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return connection;
//    }
    public void insert(Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "
                + "customer (Name, Birthday, Gender, Mail, Phone, Address) "
                + "VALUES (?, ?, ?, ?, ?, ?)");) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setDate(2, customer.getBirthday());
            preparedStatement.setInt(3, customer.getGender());
            preparedStatement.setString(4, customer.getMail());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getAddress());
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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM customer")) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("ID"));
                customer.setName(resultSet.getString("Name"));
                customer.setBirthday(resultSet.getDate("Birthday"));
                customer.setGender(resultSet.getInt("Gender"));
                customer.setMail(resultSet.getString("Mail"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setModifyDateTime(resultSet.getTimestamp("ModifyDateTime"));
                System.out.println(customer.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void select(Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "customer WHERE ID=?");) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, customer.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    customer = new Customer();
                    customer.setId(resultSet.getInt("ID"));
                    customer.setName(resultSet.getString("Name"));
                    customer.setBirthday(resultSet.getDate("Birthday"));
                    customer.setGender(resultSet.getInt("Gender"));
                    customer.setMail(resultSet.getString("Mail"));
                    customer.setPhone(resultSet.getString("Phone"));
                    customer.setAddress(resultSet.getString("Address"));
                    customer.setModifyDateTime(resultSet.getTimestamp("ModifyDateTime"));
                    System.out.println(customer.toString());
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

    public void update(Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer "
                + "SET Name=?, Birthday=?, Gender=?, Mail=?, Phone=?, Address=? "
                + "WHERE ID=?");) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setDate(2, customer.getBirthday());
            preparedStatement.setInt(3, customer.getGender());
            preparedStatement.setString(4, customer.getMail());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setInt(7, customer.getId());
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

    public void delete(Customer customer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM "
                + "customer WHERE ID=?")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, customer.getId());
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

    public boolean validate(String account, String password) {
        boolean status = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "
                + "customer WHERE Mail=? AND Password=?")) {
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                status = resultSet.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public String findNameByMail(String account) {
        String username = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT Name FROM "
                + "customer WHERE Mail=?")) {
            preparedStatement.setString(1, account);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    username = resultSet.getString("Name");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }

    public int findIdByMail(String account) {
        int userId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM "
                + "customer WHERE Mail=?")) {
            preparedStatement.setString(1, account);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userId = resultSet.getInt("ID");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userId;
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
