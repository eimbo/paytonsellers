/**
 * DAO.java
 * 
 */
package com.paytonsellersbooks.dao;

import java.sql.Connection;

public interface DAO {
	Connection getConnection();
	void closeResources(Connection conn);
}
