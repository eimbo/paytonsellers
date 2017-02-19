/**
 * CustomerDAOImpl.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.paytonsellersbooks.model.Address;
import com.paytonsellersbooks.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {
		private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer "
				+ "(cus_first, cus_last, cus_email, cus_pass, cus_date) "
				+ "VALUES (?, ?, ?, ?, ?)";
		private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer "
				+ "SET cus_first = ?, cus_last = ?, cus_email = ?, cus_pass = ?";
		private static final String SELECT_CUSTOMER_BYEMAIL = "SELECT cus_id "
				+ "FROM customer WHERE cus_email = ? LIMIT 1";
		private static final String SELECT_ADDRESS_BYCUSID = "SELECT * FROM address "
				+ "WHERE cus_id = ? LIMIT 1";
		private static final String FIND_CUSTOMER_BYID = "SELECT * FROM customer "
				+ "WHERE cus_id = ? LIMIT 1";
		private static final String INSERT_ADDRESS_SQL = "INSERT INTO address (cus_id, add_l1, add_l2, add_city, add_state, add_zip, add_phone) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		private static final String UPDATE_ADDRESS_SQL = "UPDATE address SET add_l1 = ?, add_l2 = ?, add_city = ?, "
				+ "add_state = ?, add_zip = ?, add_phone = ? WHERE cus_id = ?";
		private static final String FIND_ADDRESS_BYID_SQL = "SELECT * FROM address WHERE add_id = ? LIMIT 1";
		
	/**
	 * Inserts a  new customer and returns the auto-gen primary key.
	 * @param customer 		a Customer object
	 * @param conn			a Connection
	 * @return 				returns the customers auto-gen pk id, or 0 insert fails
	 */
	public synchronized int insert(Customer customer, Connection conn) throws DAOException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int lastInsertId = 0;
		
		try{ 
			pst = conn.prepareStatement(INSERT_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, customer.getCus_first());
			pst.setString(2, customer.getCus_last());
			pst.setString(3, customer.getCus_email());
			pst.setString(4, customer.getCus_pass());
			
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			pst.setDate(5, sqlDate);
			
			pst.executeUpdate();
			
			// get generated auto_increment pk id
			rs = pst.getGeneratedKeys();
			rs.next();
			lastInsertId = rs.getInt(1);
			conn.commit();
		}catch(SQLException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			throw new DAOException("*Error inserting new customer. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null)	pst.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		
		return lastInsertId;
	} 

	/**
	 * Update a customer in the database and return the number of rows affected.
	 * @param customer
	 * @param conn
	 * @return			the number of rows affected.
	 */
	public synchronized int update(Customer customer, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		int rows = 0;
		
		try{ 
			pst = conn.prepareStatement(UPDATE_CUSTOMER_SQL);
			pst.setString(1, customer.getCus_first());
			pst.setString(2, customer.getCus_last());
			pst.setString(3, customer.getCus_email());
			pst.setString(4, customer.getCus_pass());
	
			rows = pst.executeUpdate();

			conn.commit();
		
		}catch(SQLException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			throw new DAOException("*Error updating customer. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null)	pst.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		return rows;
	} 

	/**
	 * Remove a customer from the database.
	 * @param cusId
	 * @param conn
	 * @return				the number of rows affected.
	 */
	public synchronized int delete(Integer cusId, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * Sets the a users role ('user') in the tomcat_roles table and return the number of rows affected.
	 * @param customer
	 * @param conn
	 * @return 				returns the number of rows affected
	 */
	public synchronized int setRole(Customer customer, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		String setRole = "insert into tomcat_roles (cus_email, tomcat_role) values (?, 'user')";
		
		int result = 0;
		try{
			pst = conn.prepareStatement(setRole);
			pst.setString(1, customer.getCus_email());
			result = pst.executeUpdate();		
		}catch(SQLException e){
			throw new DAOException("*Error setting role. \n" + e.getMessage());
		}
		
		try {
			if(pst != null) pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/**
	 * Returns a customer id found by customer email.
	 * @param email 
	 * @param conn
	 * @return 			returns customer id and 0 if customer not found.
	 */
	public synchronized int findCustomerIdByEmail(String email, Connection conn) throws DAOException {
		int cusId = -1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(SELECT_CUSTOMER_BYEMAIL);
			pst.setString(1, email);
			rs = pst.executeQuery(); 
			if(!rs.next()){
				cusId = -1;
			}else{
				do{
					cusId = rs.getInt("cus_id");
				}while(rs.next());
			}
		}catch(SQLException e){
			cusId = -1;
			throw new DAOException("Error finding customer id by email. " + e.getMessage());
		}finally{
			try {
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cusId;
	}
	
	/**
	 * Returns an address found by customer id, or a new Address if not found
	 * @param cusId
	 * @param conn
	 * @return      		returns an address for the customer id and if no address is found
	 * 						a new address is returned (address id will be 0)
	 */
	public Address findCustomerAddress(int cusId, Connection conn) throws DAOException{
		
		Address address = new Address();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(SELECT_ADDRESS_BYCUSID);
			pst.setInt(1, cusId);
			rs = pst.executeQuery();
			while(rs.next()){
				address.setAdd_id(rs.getInt("add_id"));
				address.setAdd_l1(rs.getString("add_l1"));
				address.setAdd_l2(rs.getString("add_l2"));
				address.setAdd_city(rs.getString("add_city"));
				address.setAdd_state(rs.getString("add_state"));
				address.setAdd_zip(rs.getString("add_zip"));
				address.setAdd_phone(rs.getInt("add_phone"));
			}
		}catch(SQLException e){
			throw new DAOException("Error getting address. " + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return address;
	}
	
	/**
	 * Returns a customer from the database by customer id, or a new Customer if not found.
	 * @param cusId		id
	 * @param conn				a connection
	 * @return Customer			returns a customer found by it's id, and a new customer not found
	 * 							(the customers id will be 0, the first name will be null)
	 */
	public synchronized Customer findCustomerById(Integer cusId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Customer customer = new Customer();
	
		try{
			pst = conn.prepareStatement(FIND_CUSTOMER_BYID);
			pst.setInt(1, cusId);
			rs = pst.executeQuery();
			while(rs.next()){
				customer.setCus_id(rs.getInt("cus_id"));
				customer.setCus_first(rs.getString("cus_first"));
				customer.setCus_last(rs.getString("cus_last"));
				customer.setCus_email(rs.getString("cus_email"));
				customer.setCus_pass(rs.getString("cus_pass"));
				java.util.Date date = new java.util.Date(rs.getDate("cus_date").getTime());
					customer.setCus_date(date);		
					
			}
			rs.close();
			pst.close();
		}catch(SQLException e){
			customer = new Customer();
			throw new DAOException("Error finding customer by id. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return customer;
	}

	/**
	 * Inserts an address for the customer id and returns the auto-gen primary key, 
	 * and returns 0 if failure or address already exists for that customer.
	 * @param address		an address 
	 * @param cusId			a customer id
	 * @return  			returns the auto-gen primary key id, and 0 if failure
	 */
	public synchronized int insertAddress(Address address, int cusId, Connection conn) throws DAOException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		int lastInsertId = 0;
		try{
			pst = conn.prepareStatement(INSERT_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, cusId);
			pst.setString(2, address.getAdd_l1());
			pst.setString(3, address.getAdd_l2());
			pst.setString(4, address.getAdd_city());
			pst.setString(5, address.getAdd_state());
			pst.setString(6, address.getAdd_zip());
			pst.setInt(7, address.getAdd_phone());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			rs.next();
			lastInsertId = rs.getInt(1);
		}catch(SQLException e){
			lastInsertId = 0;
			throw new DAOException("Error inserting address. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return lastInsertId;
	}
	
	/**
	 * Updates an address for the customer id, and returns the number of rows affected.
	 * @param address		an address
	 * @param addId			an address id
	 * @param cusId			a customer id
	 * @param conn
	 * @return   			number of rows affected, returns null if error
	 */
	public synchronized int updateAddress(Address address, int addId, int cusId, Connection conn) throws DAOException{
		PreparedStatement pst = null;

		int rows = 0;
		
		try{ 
			pst = conn.prepareStatement(UPDATE_ADDRESS_SQL);
			pst.setString(1, address.getAdd_l1());
			pst.setString(2,  address.getAdd_l2());
			pst.setString(3, address.getAdd_city());
			pst.setString(4, address.getAdd_state());
			pst.setString(5, address.getAdd_zip());
			pst.setInt(6, address.getAdd_phone());
			pst.setInt(7, cusId);
			rows = pst.executeUpdate();
		}catch(SQLException e){
			throw new DAOException("Error updating address. " + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return rows;

	}

	/**
	 * Returns an address found by its primary key id, and returns a new Address if not found.
	 * @param addId			an address id
	 * @param conn			a connection
	 * @return      		returns the address found by its id, and a new Address if not found (the 
	 * 						address id will be 0 and other fields null)
	 */
	public synchronized Address findAddressById(int addId, Connection conn) throws DAOException{

		Address address = new Address();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(FIND_ADDRESS_BYID_SQL);
			pst.setInt(1, addId);
			rs = pst.executeQuery();
			while(rs.next()){
				address.setAdd_id(rs.getInt("add_id"));
				address.setAdd_l1(rs.getString("add_l1"));
				address.setAdd_l2(rs.getString("add_l2"));
				address.setAdd_city(rs.getString("add_city"));
				address.setAdd_state(rs.getString("add_state"));
				address.setAdd_zip(rs.getString("add_zip"));
				address.setAdd_phone(rs.getInt("add_phone"));
			}
			
		}catch(SQLException e){
			throw new DAOException("Error finding address. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return address;
	}
	
	
	
}
