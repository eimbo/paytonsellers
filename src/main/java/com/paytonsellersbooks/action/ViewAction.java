/**
 * ViewAction.java
 * 
 */

package com.paytonsellersbooks.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.paytonsellersbooks.dao.BookDAO;
import com.paytonsellersbooks.dao.CategoryDAO;
import com.paytonsellersbooks.dao.DAO;
import com.paytonsellersbooks.dao.DAOException;
import com.paytonsellersbooks.dao.DAOFactory;
import com.paytonsellersbooks.model.Book;

public class ViewAction {

	private Connection conn = null;
	
	/**
	 * Returns a HashMap of existing cats-subcats (categories as keys, and ArrayList of 
	 * subcategories as values).
	 * @return     		returns a HashMap of existing cats-subcats
	 */
	public synchronized HashMap<String, ArrayList<String>> getCategories() {
		HashMap<String, ArrayList<String>> categories = null;
		CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
		DAO dao = DAOFactory.getDAO();
		
		try{
			conn = dao.getConnection();
			categories = categoryDAO.findCategories(conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return categories;
	}
	
	/**
	 * Return top new books from a category.
	 * @param category 			a string representing a category in the database
	 * @return          		returns an array list of up to 5 books from the 
	 * 							given category
	 */
	public synchronized ArrayList<Book> getNewArrivals(String category) {
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		ArrayList<Book> newArrivals = new ArrayList<Book>();
		try{
			conn = dao.getConnection();
			newArrivals = bookDAO.findNewArrivals(category, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		
		return newArrivals;
	}
	
	/**
	 * Return the top selling books from a category.
	 * @param category 			a string representing a category.
	 * @return 					returns an array list of up to 5 best selling books
	 * 							from the category
	 */
	public synchronized ArrayList<Book> getBestSelling(String category) {
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		ArrayList<Book> bestSelling = new ArrayList<Book>();
		try{
			conn = dao.getConnection();
			bestSelling = bookDAO.findBestSelling(category, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		
		return bestSelling;
	}

	/**
	 * Returns a book by id. 
	 * @param bookId 		a book id
	 * @return  			returns the book fetched by it's id, and a new Book 
	 * 						if no book was found.
	 */
	public synchronized Book getBookById(int bookId){
		Book book = new Book();
		
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		try{
			conn = dao.getConnection();
			book = bookDAO.findBookById(bookId, conn); 
		}catch (DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return book;
	}
	
	/**
	 * Returns books from category and subcategory
	 * @param category			a category
	 * @param subcategory		a subcategory
	 * @return           		returns an array list of all books in a cat-subcat
	 */
	public synchronized ArrayList<Book> getBooksInCatSubcat(String category, String subcategory){
		
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		ArrayList<Book> books = new ArrayList<Book>();
		try{
			conn = dao.getConnection();
			books = bookDAO.findBooksInCatSubcat(category, subcategory, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			if(conn != null) dao.closeResources(conn);
		}
		return books;
	}
	/**
	 * Returns book results from search
	 * @return 			returns an array list of books from search query
	 */
	public synchronized ArrayList<Book> getBooksFromSearchQuery(String query){
		ArrayList<Book> books = new ArrayList<Book>();
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		
		try{
			conn = dao.getConnection();
			books = bookDAO.findBooksFromQuery(query, conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return books;
	}
	
	/**
	 * Returns books for the front page
	 * @return        	returns an array list of up to 30 books in no
	 * 					specific category or subcatgory. 
	 */
	public synchronized ArrayList<Book> getAllBooks(){
		ArrayList<Book> books = new ArrayList<Book>();
		BookDAO bookDAO = DAOFactory.getBookDAO();
		DAO dao = DAOFactory.getDAO();
		
		try{
			conn = dao.getConnection();
			books = bookDAO.findAllBooks(conn);
		}catch(DAOException e){
			e.printStackTrace();
		}finally{
			dao.closeResources(conn);
		}
		return books;
	}

}
