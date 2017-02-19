/**
 * CustomerAction.java
 * 
 */

package com.paytonsellersbooks.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.paytonsellersbooks.dao.CustomerDAO;
import com.paytonsellersbooks.dao.DAO;
import com.paytonsellersbooks.dao.DAOException;
import com.paytonsellersbooks.dao.DAOFactory;
import com.paytonsellersbooks.dao.InvoiceDAO;
import com.paytonsellersbooks.dao.InvoiceDetailDAO;
import com.paytonsellersbooks.model.Address;
import com.paytonsellersbooks.model.Customer;
import com.paytonsellersbooks.model.Invoice;
import com.paytonsellersbooks.model.InvoiceDetail;

public class CustomerAction {
	private Connection conn = null;
	
	/**
	 * Registers new customer in the database.
	 * @param customer 		a Customer object
	 * @return customer 	returns the Customer with updated auto-gen pk and default
	 * 						values, by fetching the customer by the returned pk id of insert.
	 * 						The returned customer's id field is 0 if failure occurs.
	 */
	public synchronized Customer registerUser(Customer customer) {
		CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
		DAO dao = DAOFactory.getDAO();
		int cusId = 0;
		Customer c = new Customer();

		try{
			conn = dao.getConnection();
			conn.setAutoCommit(false); 
			cusId = customerDAO.insert(customer, conn);
			c = customerDAO.findCustomerById(cusId, conn);
			customerDAO.setRole(c, conn);
			conn.commit();
		}catch(SQLException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			c.setCus_id(0); // it will be zero anyway
			e.printStackTrace();
		}catch(DAOException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			c.setCus_id(0); // it will be zero anyway
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		
		return c;
	}
	
	/**
	 * Returns customer id by customer email.
	 * @param email		customers email
	 * @return  		returns the id if found and -1 otherwise;
	 */
	public synchronized int getIdAtLogin(String email){
		CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
		DAO dao = DAOFactory.getDAO();
		
		int id = -1;
		
		try{
			conn = dao.getConnection();
			id = customerDAO.findCustomerIdByEmail(email, conn);
			dao.closeResources(conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return id;
	}

	/**
	 * Returns a customer with the address field of customer set.
	 * @param cusId			customer id
	 * @return       		returns a Customer object with the address field set and
	 * 						if no customer is found the customer id will be 0 and name 
	 * 						null
	 */
	public synchronized Customer getCustomerAndAddress(int cusId){
		CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
		DAO dao = DAOFactory.getDAO();
		Customer customer = new Customer();
		Address address = new Address();
		
		try{
			conn = dao.getConnection();
			customer = customerDAO.findCustomerById(cusId, conn);
			address = customerDAO.findCustomerAddress(cusId, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			if(conn != null)
				dao.closeResources(conn);
		}
		
		customer.setAddress(address);
		
		return customer;
	}
	
	/**
	 * Saves or updates a customers address and returns the address updated with auto-gen
	 * primary key.
	 * @param address		an Address object
	 * @param cusId			the customers id
	 * @return      		returns the address with auto-gen pk id and default values set
	 * 						by fetching the address by it's pk id returned from insertion
	 */
	public synchronized Address saveAddress(Address address, int cusId){
		CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
		DAO dao = DAOFactory.getDAO();
		Address add = new Address();
		
		// query an address by cusId and if none, insert one. Also return the add
		try{
			conn = dao.getConnection();
			add = customerDAO.findCustomerAddress(cusId, conn);
			int newPkId = 0;
			if(add.getAdd_id() == 0){ // no address for that customer
				newPkId = customerDAO.insertAddress(address, cusId, conn);
			}else{
				customerDAO.updateAddress(address, address.getAdd_id(), cusId, conn);
			}
			if(newPkId == 0){ // this was an insert so find by address.getAdd_id
				add = customerDAO.findAddressById(address.getAdd_id(), conn);
			}else{ 
				add = customerDAO.findAddressById(newPkId, conn);
			}
			
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return add;
	}
	
	/**
	 * Returns an array list of all invoices found for a customer id.
	 * @param cusId			the customer's id
	 * @return           	an array list of Invoice objects with the invoice-detail field
	 * 						set for each Invoice. The Invoice's invoice-detail array is 
	 * 						sorted by line number.
	 */
	public synchronized ArrayList<Invoice> getAllInvoices(int cusId){
		ArrayList<Invoice> invoices = null;
		InvoiceDAO invoiceDAO = DAOFactory.getInvoiceDAO();
		InvoiceDetailDAO invoiceDetailDAO = DAOFactory.getInvoiceDetailDAO();
		DAO dao = DAOFactory.getDAO();
		
		try{
			conn = dao.getConnection();
			invoices = invoiceDAO.findInvoicesByCustomerId(cusId, conn);
		
			for(Invoice s : invoices){
				int invId = s.getInv_id();
				ArrayList<InvoiceDetail> invDetails = 
						invoiceDetailDAO.findInvoiceDetailsByInvoiceId(invId, conn);
				s.setInvoice_detail(invDetails);
			}
			
		}catch(DAOException e){
			invoices = null;
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		
		return invoices;
		
	}
	
	/**
	 * Returns an array list of InvoiceDetail's found for an invoice id.
	 * @param invId
	 * @return				an array list of InvoiceDetail's found for an invoice id.
	 */
	public synchronized ArrayList<InvoiceDetail> getInvoiceDetailsByInvId(int invId){
		ArrayList<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
		InvoiceDetailDAO invoiceDetailDAO = DAOFactory.getInvoiceDetailDAO();
		DAO dao = DAOFactory.getDAO();
		try{
			conn = dao.getConnection();
			invoiceDetails = invoiceDetailDAO.findInvoiceDetailsByInvoiceId(invId, conn);
			
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}

		return invoiceDetails;
	}
	
	/**
	 * Returns an invoice found by it's primary key id, and a new Invoice if not found.
	 * @param invId
	 * @return				an invoice found by it's primary key id, and a new Invoice if not found.
	 */
	public synchronized Invoice getInvoiceById(int invId){
		
		Invoice invoice = new Invoice();
		InvoiceDAO invoiceDAO = DAOFactory.getInvoiceDAO();
		DAO dao = DAOFactory.getDAO();
		try{
			conn = dao.getConnection();
			invoice = invoiceDAO.findInvoiceById(invId, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
	
		return invoice;
	}

	/**
	 * Save an invoice in the database. 
	 * @return 		returns an invoice (invoice-details is not a field).
	 */
	public synchronized Invoice saveInvoice(Invoice invoice, ArrayList<InvoiceDetail> invoiceDetails, 
				int cusId){
		InvoiceDAO invoiceDAO = DAOFactory.getInvoiceDAO();
		InvoiceDetailDAO invoiceDetailDAO = DAOFactory.getInvoiceDetailDAO();
		DAO dao = DAOFactory.getDAO();
		
		Invoice inv = new Invoice();
		
		int invoiceId = 0;
		try{
			conn = dao.getConnection();
			conn.setAutoCommit(false);
			invoiceId = invoiceDAO.insert(invoice, cusId, conn);
			inv = invoiceDAO.findInvoiceById(invoiceId, conn);
			if(invoiceId > 0){
				for(InvoiceDetail invd : invoiceDetails){
					invoiceDetailDAO.insert(invd, invoiceId, conn);
				}
			}
			conn.commit();
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return inv;
		
	}
}
