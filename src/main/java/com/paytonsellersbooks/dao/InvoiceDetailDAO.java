package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.util.ArrayList;
import com.paytonsellersbooks.model.InvoiceDetail;

public interface InvoiceDetailDAO {

	int insert(InvoiceDetail invoiceDetail, int invoiceId, Connection conn) throws DAOException;
	int udpate(InvoiceDetail invoiceDetail, Connection conn) throws DAOException;
	int delete(Integer invoiceDetailId, Connection conn) throws DAOException;
	InvoiceDetail findInvoiceDetailById(Integer invoiceDetailId, Connection conn) throws DAOException;
	ArrayList<InvoiceDetail> findInvoiceDetailsByInvoiceId(int invId, Connection conn) throws DAOException;


}
