/**
 * BookDAOImpl.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.paytonsellersbooks.model.Book;

public class BookDAOImpl implements BookDAO {
	private static final String INSERT_BOOK_SQL = "INSERT INTO book "
			+ "(book_title, book_author_first, book_author_last, book_descript, "
			+ "book_publisher, book_pubyear, book_isbn, book_length, book_dim, book_format, "
			+ "book_price, book_img, book_date) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_BOOK_SQL = "UPDATE book "
			+ "SET book_title = ?, book_author_first = ?, book_author_last = ?, book_descript = ?, "
			+ "book_publisher = ?, book_pubyear = ?, book_isbn = ?, book_length = ?, book_dim = ?, "
			+ "book_format = ?, book_price = ?, book_img = ?, book_date = ? "
			+ "WHERE book_id = ?";
	private static final String DELETE_BOOK_SQL = "DELETE FROM book WHERE book_id = ?";
	private static final String FIND_BOOK_BYID_SQL = "SELECT * FROM book WHERE book_id = ? LIMIT 1";
	private static final String SELECT_TOPSELLING_SQL = "SELECT book_id, book_title, book_author_first, "
			+ "book_author_last, book_price, book_img FROM book NATURAL JOIN book_category "
			+ "NATURAL JOIN category WHERE category.cat_category = ? GROUP BY book_id "
			+ "ORDER BY book.book_buycounter DESC LIMIT 5";
	private static final String SELECT_NEWARRIVALS_SQL = "SELECT book_id, book_title, book_author_first, "
			+ "book_author_last, book_price, book_img FROM book NATURAL JOIN book_category "
			+ "NATURAL JOIN category WHERE category.cat_category = ? GROUP BY book_id "
			+ "ORDER BY book_date DESC LIMIT 5";
	private static final String SELECT_FROMCATEGORY_SQL = "SELECT book_id, book_title, book_author_first, "
			+ "book_author_last, book_price, book_img FROM book NATURAL JOIN book_category "
			+ "NATURAL JOIN category WHERE cat_category = ? LIMIT 30";
	private static final String SELECT_BOOKS_INCATSUBCAT_SQL = "SELECT book_id, book_title, "
			+ "book_author_first, book_author_last, book_price, book_img FROM book "
			+ "WHERE book_id IN (SELECT book_id FROM book NATURAL JOIN book_category NATURAL JOIN category "
			+ "WHERE category.cat_category = ? AND category.cat_subcategory = ?)";
	private static final String SELECT_ALL_BOOKS_SQL = "SELECT book_id, book_title, book_author_first, book_author_last, book_price, book_img "
			+ "FROM book LIMIT 30";
	private static final String SELECT_ALL_BOOKS_USERQUERY = "SELECT book_id, book_title, book_author_first, book_author_last, book_price, book_img "
			+ "FROM book NATURAL JOIN book_category NATURAL JOIN category WHERE book_title LIKE ? OR book_author_first LIKE ? "
			+ "OR book_author_first LIKE ? OR book_descript LIKE ? OR book_publisher LIKE ? OR book_isbn LIKE ? "
			+ "OR cat_category LIKE ? OR cat_subcategory LIKE ? GROUP BY book_title";
	
	
	/**
	 * Inserts a book into the database and returns the auto-gen primary key.
	 * @param book		a book
	 * @param conn		a connection
	 * @return 			returns the auto-generated pk id for the inserted book and 
	 * 					returns 0 if no insert occurs. 
	 * @throws DAOException
	 */
	public synchronized int insert(Book book, Connection conn) throws DAOException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Integer lastInsertId = 0;
		
		try{ 

			pst = conn.prepareStatement(INSERT_BOOK_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, book.getBook_title());
			pst.setString(2, book.getBook_author_first());
			pst.setString(3, book.getBook_author_last());
			pst.setString(4, book.getBook_descript());
			pst.setString(5, book.getBook_publisher());
			pst.setInt(6, book.getBook_pubyear());
			pst.setString(7, book.getBook_isbn());
			pst.setString(8, book.getBook_length());
			pst.setString(9, book.getBook_dim());
			pst.setString(10, book.getBook_format());
			pst.setInt(11, book.getBook_price());
			pst.setString(12, book.getBook_img());
			
			if(book.getBook_date() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String strDate = "01/01/2000";
				try {
					book.setBook_date(sdf.parse(strDate));
				} catch (java.text.ParseException e) {
					throw new DAOException("Error parsing date. " + e.getMessage());
				}
			}
			try{
				// Convert from java.util.Date to java.sql.Date
				java.sql.Date sqlDate = new java.sql.Date(book.getBook_date().getTime());
				pst.setDate(13, sqlDate);
			}catch(SQLException e){
				throw new DAOException("Error setting date. " + e.getMessage());
			}
			
			pst.executeUpdate();
			
			// get generated auto_increment pk id
			// let exception if rs == null or !rs.next()
			rs = pst.getGeneratedKeys();
			rs.next();
			lastInsertId = rs.getInt(1);
		}catch(SQLException e){
			lastInsertId = 0;
			throw new DAOException("*Error inserting new book. \n" + e.getMessage());
		}finally{
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return	lastInsertId;
	}

	/**
	 * Updates a book in the database and returns the number of rows affected.
	 * @param book		
	 * @param conn
	 * @return			the number of rows affected by the update
	 * @throws DAOException
	 */
	public synchronized int update(Book book, Connection conn) throws DAOException {
		PreparedStatement pst = null;

		int rows = 0;
		
		try{ 
			pst = conn.prepareStatement(UPDATE_BOOK_SQL);
			pst.setString(1, book.getBook_title());
			pst.setString(2, book.getBook_author_first());
			pst.setString(3, book.getBook_author_last());
			pst.setString(4, book.getBook_descript());
			pst.setString(5, book.getBook_publisher());
			pst.setInt(6, book.getBook_pubyear());
			pst.setString(7, book.getBook_isbn());
			pst.setString(8, book.getBook_length());
			pst.setString(9, book.getBook_dim());
			pst.setString(10, book.getBook_format());
			pst.setInt(11, book.getBook_price());
			pst.setString(12, book.getBook_img());

			if(book.getBook_date() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String strDate = "01/01/2000";
				try {
					book.setBook_date(sdf.parse(strDate));
				} catch (java.text.ParseException e) {
					throw new DAOException("Error parsing date. " + e.getMessage());
				}
			}
			try{
				// Convert from java.util.Date to java.sql.Date
				java.sql.Date sqlDate = new java.sql.Date(book.getBook_date().getTime());
				pst.setDate(13, sqlDate);
			}catch(SQLException e){
				throw new DAOException("Error setting date. " + e.getMessage());
			}
			

			pst.setInt(14, book.getBook_id());
		
			rows = pst.executeUpdate();
			
		}catch(SQLException e){
			throw new DAOException("*Error updating book. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return rows;
	}

	/**
	 * Removes a book from the database by it's id and returns the number of rows affected.
	 * @param bookId		
	 * @param conn	
	 * @return 				the number of rows affected
	 * @throws DAOException
	 */
	public synchronized int delete(int bookId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		int rows = 0;
		try{
			pst = conn.prepareStatement(DELETE_BOOK_SQL);
			pst.setInt(1, bookId);
			rows = pst.executeUpdate();
		}catch(SQLException e){
			throw new DAOException("Error deleting book. \n" + e.getMessage());
		}finally{
			try {
				if(pst != null)	pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
	
	/**
	 * Returns a book found by it's id.
	 * @return book 		the book found by it's id or new Book if no book was found
	 * @param bookId		the id of the book to find
	 * @param conn			
	 * @throws DAOException
	 */
	public synchronized Book findBookById(int bookId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Book book = new Book();
		
		try{
			pst = conn.prepareStatement(FIND_BOOK_BYID_SQL);
			pst.setInt(1, bookId);
			rs = pst.executeQuery();
			
			if(!rs.next()){
				pst.close();
				rs.close();
			}else{
				do{
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_author_first(rs.getString("book_author_first"));
					book.setBook_author_last(rs.getString("book_author_last"));
					book.setBook_descript(rs.getString("book_descript"));
					book.setBook_publisher(rs.getString("book_publisher"));
					book.setBook_pubyear(rs.getInt("book_pubyear"));
					book.setBook_isbn(rs.getString("book_isbn"));
					book.setBook_length(rs.getString("book_length"));
					book.setBook_dim(rs.getString("book_dim"));
					book.setBook_format(rs.getString("book_format"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_buycounter(rs.getInt("book_buycounter"));
					book.setBook_viewcounter(rs.getInt("book_viewcounter"));
					book.setBook_img(rs.getString("book_img"));
					
					
					// Convert sql.Date to util.Date
					java.util.Date date = new java.util.Date(rs.getDate("book_date").getTime());
					book.setBook_date(date);	
					
				}while(rs.next());
			}
		}catch(SQLException e){
			throw new DAOException("*Error finding book by id. \n" + e.getMessage());
		}finally{
			try {
				if(pst != null)	pst.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return book;
	}

	/**
	 * Select (30) books by category.
	 * @param category			a category to search for books
	 * @param conn
	 * @return 					an array list of books from the given category
	 * @throws DAOException
	 */
	public synchronized ArrayList<Book> findBooksByCategory(String category, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Book> byCategory = new ArrayList<Book>();
		try{
			pst = conn.prepareStatement(SELECT_FROMCATEGORY_SQL);
			pst.setString(1, category);
			rs = pst.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_author_first(rs.getString("book_author_first"));
				book.setBook_author_last(rs.getString("book_author_last"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_img(rs.getString("book_img"));
				byCategory.add(book);
			}

		}catch(SQLException e){
			throw new DAOException("Error selecting books by category. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return byCategory;
	}

	/**
	 * Select the 5 best selling from a category.
	 * @param category			the category to search for best selling
	 * @param conn
	 * @return 					an array list of the 5 best selling from the given category
	 * @throws DAOException
	 */
	public synchronized ArrayList<Book> findBestSelling(String category, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Book> bestSelling = new ArrayList<Book>();
		try{
			pst = conn.prepareStatement(SELECT_TOPSELLING_SQL);
			pst.setString(1, category);
			rs = pst.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_author_first(rs.getString("book_author_first"));
				book.setBook_author_last(rs.getString("book_author_last"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_img(rs.getString("book_img"));
				bestSelling.add(book);
			}

		}catch(SQLException e){
			throw new DAOException("Error selecting best selling. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return bestSelling;
	}
	
	/**
	 * Select 5 new arrivals from a category.
	 * @param category			
	 * @param conn
	 * @return 					an array list of the 5 newest books in the given category
	 */

	public synchronized ArrayList<Book> findNewArrivals(String category, Connection conn) throws DAOException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Book> newArrivals = new ArrayList<Book>();
		try{
			pst = conn.prepareStatement(SELECT_NEWARRIVALS_SQL);
			pst.setString(1, category);
			rs = pst.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_author_first(rs.getString("book_author_first"));
				book.setBook_author_last(rs.getString("book_author_last"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_img(rs.getString("book_img"));
				newArrivals.add(book);
			}

		}catch(SQLException e){
			throw new DAOException("Error selecting new arrivals. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return newArrivals;

	}

	/**
	 * Returns a list of books in the given category-subcategory.
	 * @param category
	 * @param subcategory
	 * @param conn
	 * @return 						an array list of books form the given category-subcategory
	 */
	public synchronized ArrayList<Book> findBooksInCatSubcat(String category, String subcategory, Connection conn) throws DAOException{
		ArrayList<Book> books = new ArrayList<Book>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(SELECT_BOOKS_INCATSUBCAT_SQL);
			pst.setString(1, category);
			pst.setString(2, subcategory);
			rs = pst.executeQuery();
			while(rs.next()){
				Book b = new Book();
				b.setBook_id(rs.getInt("book_id"));
				b.setBook_title(rs.getString("book_title"));
				b.setBook_author_first(rs.getString("book_author_first"));
				b.setBook_author_last(rs.getString("book_author_last"));
				b.setBook_price(rs.getInt("book_price"));
				b.setBook_img(rs.getString("book_img"));
				books.add(b);
			}

		}catch(SQLException e){
			throw new DAOException("Error getting books by subcategory. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return books;
	}

	/**
	 * Returns up to 30 books from the database.
	 * @param conn
	 * @return 				an array list of up to 30 books
	 */
	public synchronized ArrayList<Book> findAllBooks(Connection conn) throws DAOException{
		ArrayList<Book> books = new ArrayList<Book>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(SELECT_ALL_BOOKS_SQL);
			
			rs = pst.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_author_first(rs.getString("book_author_first"));
				book.setBook_author_last(rs.getString("book_author_last"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_img(rs.getString("book_img"));
				books.add(book);
			}

		}catch(SQLException e){
			throw new DAOException("Error getting all books. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return books;
	}
	
	/**
	 * Returns results of user search
	 * @param query			a query term used to search in title, author, descript, etc
	 * @param conn	
	 * @return 				an array list of books matching the query
	 */
	public synchronized ArrayList<Book> findBooksFromQuery(String query, Connection conn) throws DAOException{
		ArrayList<Book> books = new ArrayList<Book>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(SELECT_ALL_BOOKS_USERQUERY);
			pst.setString(1, "%" + query + "%");
			pst.setString(2, "%" + query + "%");
			pst.setString(3, "%" + query + "%");
			pst.setString(4, "%" + query + "%");
			pst.setString(5, "%" + query + "%");
			pst.setString(6, "%" + query + "%");
			pst.setString(7, "%" + query + "%");
			pst.setString(8, "%" + query + "%");
			
			rs = pst.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_author_first(rs.getString("book_author_first"));
				book.setBook_author_last(rs.getString("book_author_last"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_img(rs.getString("book_img"));
				books.add(book);
			}

		}catch(SQLException e){
			throw new DAOException("Error getting books for user query. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return books;
	}

}
