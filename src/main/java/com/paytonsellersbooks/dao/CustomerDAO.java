/**
 * CustomerDAO.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;

import com.paytonsellersbooks.model.Address;
import com.paytonsellersbooks.model.Customer;

public interface CustomerDAO {

	int insert(Customer customer, Connection conn) throws DAOException;
	int update(Customer customer, Connection conn) throws DAOException;
	int delete(Integer customerId, Connection conn)throws DAOException;
	int setRole(Customer customer, Connection conn)throws DAOException;
	
	int findCustomerIdByEmail(String email, Connection conn) throws DAOException;
	Address findCustomerAddress(int cusId, Connection conn) throws DAOException;
		
	Customer findCustomerById(Integer customerId, Connection conn) throws DAOException;
	int insertAddress(Address address, int cusId, Connection conn) throws DAOException;
	int updateAddress(Address address, int addId, int cusId, Connection conn) throws DAOException;
	Address findAddressById(int addId, Connection conn) throws DAOException;

	
}
