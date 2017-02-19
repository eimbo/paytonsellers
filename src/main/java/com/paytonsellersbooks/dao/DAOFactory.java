package com.paytonsellersbooks.dao;

public class DAOFactory {
	public static DAO getDAO(){
		return new BaseDAO();
	}
	public static CustomerDAO getCustomerDAO(){
		return new CustomerDAOImpl();
	}
	public static BookDAO getBookDAO(){
		return new BookDAOImpl();
	}
	public static CategoryDAO getCategoryDAO(){
		return new CategoryDAOImpl();
	}
	public static InvoiceDAO getInvoiceDAO(){
		return new InvoiceDAOImpl();
	}
	public static InvoiceDetailDAO getInvoiceDetailDAO(){
		return new InvoiceDetailDAOImpl();
	}
	
}
