/**
 * AdminAction.java
 *  
 */

package com.paytonsellersbooks.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.paytonsellersbooks.dao.BookDAO;
import com.paytonsellersbooks.dao.CategoryDAO;
import com.paytonsellersbooks.dao.DAO;
import com.paytonsellersbooks.dao.DAOException;
import com.paytonsellersbooks.dao.DAOFactory;
import com.paytonsellersbooks.model.Book;

public class AdminAction {

	private Connection conn = null;
	
	/**
	 * Add a book to the database.
	 * @param book		the book to add to the database 
	 * @return 			a Book object with updated fields, including auto-gen pk and default values, 
	 * 					by fetching the book after the insertion.
	 */
	public synchronized Book addBook(Book book){
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		Book b = new Book();
		
		try{
			conn = dao.getConnection();
			conn.setAutoCommit(false);
			int id = bookDAO.insert(book, conn);
			b = bookDAO.findBookById(id, conn);
			conn.commit();
		}catch (SQLException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}catch (DAOException e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
	
		return b;
	}
	
	/**
	 * Removes a book from the database.
	 * @param bookId	the pk id of the book to be removed
	 * @return    		the number of rows affected, 0 or 1
	 */
	public synchronized int removeBook(int bookId){
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		int flag = 0;
		try{
			conn = dao.getConnection();
			flag = bookDAO.delete(bookId, conn);
			dao.closeResources(conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			if(conn != null) dao.closeResources(conn);
		}	
		
		return flag;
	}
	
	/**
	 * Fetch a book by its pk id.
	 * @param bookId	the pk id of the book to fetch
	 * @return       	returns a Book found by its id, and a new Book if 
	 * 					no book was found (book_id will be 0 and book_title
	 * 					will be null).
	 */
	public synchronized Book getBookById(int bookId){
		Book book = new Book();
		
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		try{
			conn = dao.getConnection();
			book = bookDAO.findBookById(bookId, conn); 
			dao.closeResources(conn);
		}catch (DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return book;
	}
	
	/**
	 * Update an existing book.
	 * @param book 		an updated book object
	 * @return   		the number of rows affected. 
	 */
	public synchronized int updateBook(Book book){
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		
		int rows = 0;
		try{
			conn = dao.getConnection();
			rows = bookDAO.update(book, conn);
			dao.closeResources(conn);
		}catch (DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
	
		return rows;		
	}
	
	/**
	 * <p> Given the HashMap of cats-subcats for a particular book (categories as keys, values 
	 * are ArrayLists of subcats) and a bookId, this method updates the cats-subcats for the 
	 * book. </p>
	 * <p>
	 * The goal is to have no cat-subcats that do not reference a book, so the cats-subcats 
	 * always represent the books in the database. The way this method works is it deletes all 
	 * references to the book in the associative table, then deletes rows in the category table 
	 * not referencing a book in the associative. Once this is complete, then it checks if the books
	 * new (cat-subcat) exists and if not creates it, then inserts the (bookId, catId) into the 
	 * associative table.
	 * </p>
	 * @param list			a HashMap with a books new categories as keys and values for each key
	 * 						is an array list of subcategories
	 * @param bookId		the id of the book whose cats-subcats is being updated
	 * @return int      		returns 1 if successful, -1 if failure.
	 */
	public synchronized int setCategories(HashMap<String, ArrayList<String>> list, int bookId){
		CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
		DAO dao = DAOFactory.getDAO();
		
		int result = 1;
		
		try{
			conn = dao.getConnection();
			conn.setAutoCommit(false);
			categoryDAO.deleteCategoryReferences(bookId, conn);
			for(String key : list.keySet()){ // each key in HashMap
				for(String s : list.get(key)){ // each String in Array 
					Integer catId = null;
					catId = categoryDAO.doesCatAndSubcatExist(key, s, conn);
					if(catId == null){ // does not exist so insert it
						catId = categoryDAO.insert(key, s, conn);
					}
					categoryDAO.insertBookCategoryKeys(bookId, catId, conn);
				}
			}
			conn.commit();
		}catch (SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			result = -1;
			e.printStackTrace();
		}catch (DAOException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			result = -1;
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		
		return result;
		
	}

	/**
	 * Returns a HashMap of the current cats-subcats that the book belongs to. 
	 * @param bookId		the id of the book 
	 * @return      		the HashMap (cats as keys, subcats in arraylists set as values)
	 */
	public synchronized HashMap<String, ArrayList<String>> getBooksCategories(int bookId) {
		HashMap<String, ArrayList<String>> bookCats = new HashMap<String, ArrayList<String>>();
		CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
		DAO dao = DAOFactory.getDAO();
		try{
			conn = dao.getConnection();
			bookCats = categoryDAO.getBooksCategories(bookId, conn);
			dao.closeResources(conn);
		}catch(DAOException e){
			e.printStackTrace();
		}
		
		return bookCats;
	}
}
