package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.paytonsellersbooks.model.Invoice;

public interface InvoiceDAO {

	int insert(Invoice invoice, int cusId, Connection conn) throws DAOException;
	int update(Invoice invoice, Connection conn) throws DAOException;
	int delete(Integer invoiceId, Connection conn) throws DAOException;
	Invoice findInvoiceById(Integer invoiceId, Connection conn) throws DAOException;
	ArrayList<Invoice> findInvoicesByCustomerId(int customerId, Connection conn) throws DAOException;
	
}
