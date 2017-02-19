/**
 * InvoiceDetailDAOImpl.java
 *
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.paytonsellersbooks.model.InvoiceDetail;

public class InvoiceDetailDAOImpl implements InvoiceDetailDAO {
	private static final String FIND_INVDETAILS_BYINVID = "SELECT * FROM invoice_detail WHERE inv_id = ?";
	private static final String INSERT_INVDETAIL_SQL = "INSERT INTO invoice_detail (inv_id, invdet_bookid, "
			+ "invdet_title, invdet_author, invdet_isbn, invdet_line, invdet_price, invdet_units) " 
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * Insert an invoice-detail into the database belonging to the given invoice, returns the number
	 * of affected rows.
	 * @param invoiceDetail
	 * @param invoiceId
	 * @param conn
	 * @return					returns the number of rows affected
	 */
	public synchronized int insert(InvoiceDetail invoiceDetail, int invoiceId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		int rows = 0;
		try{
			pst = conn.prepareStatement(INSERT_INVDETAIL_SQL);
			pst.setInt(1, invoiceId);
			pst.setInt(2, invoiceDetail.getInvdet_bookid());
			pst.setString(3, invoiceDetail.getInvdet_title());
			pst.setString(4, invoiceDetail.getInvdet_author());
			pst.setString(5, invoiceDetail.getInvdet_isbn());
			pst.setInt(6, invoiceDetail.getInvdet_line());
			pst.setInt(7, invoiceDetail.getInvdet_price());
			pst.setInt(8, invoiceDetail.getInvdet_units());
			rows = pst.executeUpdate();
		}catch(SQLException e){
			throw new DAOException("Error inserting invoice-detail. " + e.getMessage());
		}finally{
			if(pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return rows;
	}

	/**
	 * Updates an invoice-detail and returns the number of affected rows.
	 * @param invoiceDetail
	 * @param conn
	 * @return 					the number of affected rows
	 */
	public synchronized int udpate(InvoiceDetail invoiceDetail, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Removes an invoice-detail from the database by it's primary key id and returns the 
	 * number of affected rows.
	 * @param invoiceDetailId
	 * @param conn
	 * @return 						the number of affected rows
	 */
	public synchronized int delete(Integer invoiceDetailId, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns an invoice-detail found by it's primary key id and a new InvoiceDetail if not found.
	 * @param invoiceDetailId
	 * @param conn
	 * @return						returns the invoice-detail found by it's id or a new InvoiceDetail 
	 * 								if not found
	 */
	public synchronized InvoiceDetail findInvoiceDetailById(Integer invoiceDetailId, Connection conn) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns an array list of invoice-details belonging to the given invoice id.
	 * @param invId
	 * @param conn
	 * @return				an array list of invoice details for the given invoice id
	 */
	public synchronized ArrayList<InvoiceDetail> findInvoiceDetailsByInvoiceId(int invId, Connection conn) throws DAOException{
		ArrayList<InvoiceDetail> invdetList = new ArrayList<InvoiceDetail>();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(FIND_INVDETAILS_BYINVID);
			pst.setInt(1, invId);
			rs = pst.executeQuery();
			if(!rs.next()){
				invdetList = null;
				return invdetList;
			}else{
				do{
					InvoiceDetail invdet = new InvoiceDetail();
					
					invdet.setInvdet_id(rs.getInt("invdet_id"));
					invdet.setInvdet_bookid(rs.getInt("invdet_bookid"));
					invdet.setInvdet_title(rs.getString("invdet_title"));
					invdet.setInvdet_author(rs.getString("invdet_author"));
					invdet.setInvdet_isbn(rs.getString("invdet_isbn"));
					invdet.setInvdet_line(rs.getInt("invdet_line"));
					invdet.setInvdet_price(rs.getInt("invdet_price"));
					invdet.setInvdet_units(rs.getInt("invdet_units"));
					
					invdetList.add(invdet);
				}while(rs.next());
			}
			pst.close();
			rs.close();
		}catch(SQLException e){
			throw new DAOException("Error getting invoice details. " + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return invdetList;
	}

}
