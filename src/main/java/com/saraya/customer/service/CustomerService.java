package com.saraya.customer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.saraya.customer.entities.Customer;

@Service
public class CustomerService {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/customer_db?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	private static final String INSERT_CUSTOMERS_SQL = "INSERT INTO customer" + " (name, email, phone, address) VALUES " + " (?, ?, ?, ?);";
	private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer;";
	private static final String SELECT_CUSTOMER_BY_ID = "SELECT id,name,email,phone,address FROM customer WHERE id=?";
	private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customer WHERE id=?;";
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer SET name=?, email=?, phone=?, address=? WHERE id=?;";
	
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			 e.printStackTrace();
        }
		return connection;
	}
	
	// 
	public List<Customer> selectAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		 try (
				 Connection connection = getConnection();

		            // Step 2:Create a statement using connection object
		         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS);)
		 {

	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                String address = rs.getString("address");
	                customers.add(new Customer(id, name, email, phone, address));
		        }
		 } catch (SQLException e) {
		            printSQLException(e);
		        }
		   return customers;
	}
	
	//
	 public Customer selectCustomerById(int id) {
	        Customer customer = null;
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);) {
	            preparedStatement.setInt(1, id);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                String address = rs.getString("address");
	                customer = new Customer(id, name, email, phone, address);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return customer;
	 }
	 
	 public void insertCustomer(Customer customer) throws SQLException {
	        System.out.println(INSERT_CUSTOMERS_SQL);
	        // try-with-resource statement will auto close the connection.
	        try (Connection connection = getConnection(); 
	        	PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMERS_SQL)) {
	            preparedStatement.setString(1, customer.getName());
	            preparedStatement.setString(2, customer.getEmail());
	            preparedStatement.setString(3, customer.getPhone());
	            preparedStatement.setString(4, customer.getAddress());
	            System.out.println(preparedStatement);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }
	
	 
	 public boolean deleteCustomer(int id) throws SQLException {
	        boolean customerDeleted;
	        try (Connection connection = getConnection();
	        	PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL);) {
	            statement.setInt(1, id);
	            customerDeleted = statement.executeUpdate() > 0;
	        }
	        return customerDeleted;
	 }
	 
	 
	 public boolean updateCustomer(Customer customer) throws SQLException {
	        boolean customerUpdated;
	        try (Connection connection = getConnection(); 
	        	PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL);) {
	            statement.setString(1, customer.getName());
	            statement.setString(2, customer.getEmail());
	            statement.setString(3, customer.getPhone());
	            statement.setString(4, customer.getAddress());
	            statement.setInt(5, customer.getId());

	            customerUpdated = statement.executeUpdate() > 0;
	        }
	        return customerUpdated;
	    }
	 
     //
	 private void printSQLException(SQLException ex) {
		 for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	    }
	}
		
}


