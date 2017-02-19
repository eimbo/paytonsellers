/**
 * BaseDAO.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDAO implements DAO {
	private DataSource ds;
	private Connection conn = null;
	
	/**
	 * Gets connection from DataSource.
	 * @return Connection
	 */
	public Connection getConnection() {
		Context initContext;
		Context envContext = null; 
		try{
			initContext = new InitialContext();
			envContext = (Context)initContext.lookup("java:comp/env");
		}catch(NamingException e){
			e.printStackTrace();
		}
		
		try{
			ds = (DataSource)envContext.lookup("jdbc/paytonsellers");
		}catch(NamingException e){
			e.printStackTrace();
		}
		
		try{
			conn = ds.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeResources(Connection conn){
		try{
			if(conn != null) conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
