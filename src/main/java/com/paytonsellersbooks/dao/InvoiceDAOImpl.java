/**
 * InvoiceDAOImpl.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.paytonsellersbooks.model.Invoice;

public class InvoiceDAOImpl implements InvoiceDAO {
	private static final String FIND_INVOICES_BYCUSID = "SELECT * FROM invoice WHERE cus_id = ?";
	private static final String INSERT_INVOICE_SQL = "INSERT INTO invoice (cus_id, inv_date, inv_cus_first, "
			+ "inv_cus_last, inv_add_l1, inv_add_l2, inv_add_city, inv_add_state, inv_add_zip) "	
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_INVOICE_BYID_SQL = "SELECT * FROM invoice WHERE inv_id = ? LIMIT 1";
	
	/**
	 * Insert an invoice into the database and return the primary key id, returns -1 if failure or it exists.
	 * @param invoice
	 * @param cusId
	 * @param conn
	 * @return 			returns the invoice pk auto-gen id, and -1  if failure.
	 */
	public synchronized int insert(Invoice invoice, int cusId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = -1;
		try{
			pst = conn.prepareStatement(INSERT_INVOICE_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, cusId);
			Timestamp ts = new Timestamp(invoice.getInv_date().getTime());
			pst.setTimestamp(2, ts);
			pst.setString(3, invoice.getInv_cus_first());
			pst.setString(4, invoice.getInv_cus_last());
			pst.setString(5,  invoice.getInv_add_l1());
			pst.setString(6, invoice.getInv_add_l2());
			pst.setString(7,  invoice.getInv_add_city());
			pst.setString(8, invoice.getInv_add_state());
			pst.setString(9, invoice.getInv_add_zip());
			pst.executeUpdate();
			
			rs = pst.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			
			rs.close();
			pst.close();
		}catch(SQLException e){
			throw new DAOException("*Error inserting new invoice. \n" + e.getMessage());
		}finally{
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}

	/**
	 * Updates an invoice in the database and returns the number of rows affected
	 * @param invoice
	 * @param conn
	 * @return				the number of rows affected.
	 */
	public synchronized int update(Invoice invoice, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Removes an invoice from the database and returns the number of rows affected.
	 * @param invoiceId
	 * @param conn
	 * @return				the number of rows affected
	 */
	public synchronized int delete(Integer invoiceId, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns an invoice by invoice primary key id and a new invoice if not found.
	 * @param invoiceId
	 * @param conn
	 * @return 				returns the invoice found by it's primary key id or a new invoice if not found
	 * 						(the integer fields will be 0 and string fields null)
	 */
	public synchronized  Invoice findInvoiceById(Integer invoiceId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Invoice invoice = new Invoice();
		try{
			pst = conn.prepareStatement(SELECT_INVOICE_BYID_SQL);
			pst.setInt(1, invoiceId);
			rs = pst.executeQuery();
			while(rs.next()){
				invoice.setInv_id(rs.getInt("inv_id"));
				invoice.setInv_date(rs.getTimestamp("inv_date"));
				invoice.setInv_cus_first(rs.getString("inv_cus_first"));
				invoice.setInv_cus_last(rs.getString("inv_cus_last"));
				invoice.setInv_add_l1(rs.getString("inv_add_l1"));
				invoice.setInv_add_l2(rs.getString("inv_add_l2"));
				invoice.setInv_add_city(rs.getString("inv_add_city"));
				invoice.setInv_add_state(rs.getString("inv_add_state"));
				invoice.setInv_add_zip(rs.getString("inv_add_zip"));
			}
			rs.close();
			pst.close();
		}catch(SQLException e){
			throw new DAOException("Error getting invoice by id. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return invoice;
	}

	/**
	 * Returns all Invoices for a customer id, in an array list.
	 * @param customerId
	 * @param conn
	 * @return				an array list of a customers invoices
	 */
	public synchronized ArrayList<Invoice> findInvoicesByCustomerId(int customerId, Connection conn) throws DAOException {
		ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(FIND_INVOICES_BYCUSID);
			pst.setInt(1, customerId);
			rs = pst.executeQuery();
			if(!rs.next()){
				return invoiceList;
			}else{
				do{
					Invoice invoice = new Invoice();
				
					invoice.setInv_id(rs.getInt("inv_id"));
					java.util.Date date = new java.util.Date(rs.getDate("inv_date").getTime());
						invoice.setInv_date(date);	
					invoice.setInv_cus_first(rs.getString("inv_cus_first"));
					invoice.setInv_cus_last(rs.getString("inv_cus_last"));
					invoice.setInv_add_l1(rs.getString("inv_add_l1"));
					invoice.setInv_add_l2(rs.getString("inv_add_l2"));
					invoice.setInv_add_city(rs.getString("inv_add_city"));
					invoice.setInv_add_state(rs.getString("inv_add_state"));
					invoice.setInv_add_zip(rs.getString("inv_add_zip"));
					
					invoiceList.add(invoice);
					
				}while(rs.next());
			}
			pst.close();
			rs.close();
		}catch(SQLException e){
			invoiceList = null;
			throw new DAOException("Error getting invoices. " + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return invoiceList;
	}

}
