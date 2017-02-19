/**
 * CategoryDAOImpl.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryDAOImpl implements CategoryDAO {
	private static final String INSERT_CATEGORY_SQL = "INSERT INTO category (cat_category, cat_subcategory) "
			+ "VALUES (?, ?)";
	private static final String SELECT_CATEGORIES_SQL = "SELECT cat_category FROM category "
			+ "GROUP BY cat_category ORDER BY cat_category";
	private static final String SELECT_SUBCATS_OF_CAT_SQL = "SELECT cat_subcategory FROM category WHERE cat_category = ?";
	private static final String SELECT_CAT_SQL = "SELECT cat_id FROM category WHERE cat_category = ? AND cat_subcategory = ? LIMIT 1";
	private static final String INSERT_BOOK_CAT_SQL = "INSERT INTO book_category (book_id, cat_id) VALUES (?, ?)";
	private static final String DELETE_BOOKCAT_REFS_SQL = "DELETE FROM book_category WHERE book_id = ?";
	private static final String DELETE_NULL_CAT_REFS_SQL = "DELETE FROM category WHERE category.cat_id NOT IN "
			+ "(SELECT cat_id FROM book_category GROUP BY cat_id) AND category.cat_category NOT IN "
			+ "('Literature & Fiction', 'Mystery & Thriller', 'Sci-Fi & Fantasy', 'Textbooks')";
	private static final String FIND_CATSSUBCATS_FORBOOK_SQL = "SELECT category.cat_category, category.cat_subcategory "
			+ "FROM book natural join book_category natural join category WHERE book.book_id = ?";
	
	/**
	 * Insert a category-subcategory and return the auto-gen primary key
	 * @param category
	 * @param subcategory
	 * @param conn
	 * @return Integer Returns last insert pk id or null if insert failure.
	 */
	public synchronized Integer insert(String category, String subcategory, Connection conn) 
			throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Integer lastInsertId = null;
		try{
			pst = conn.prepareStatement(INSERT_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, category);
			pst.setString(2, subcategory);
			pst.executeUpdate();
			
			// get generated auto_increment pk id
			rs = pst.getGeneratedKeys();
			rs.next();
			lastInsertId = rs.getInt(1);
			conn.commit();
		}catch(SQLException e){
			try{
				lastInsertId = null;
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			throw new DAOException("*Error inserting categories. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null)	pst.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		return lastInsertId;
	}
	
	/**
	 * Update a category-subcategory
	 * @return Integer Returns integer number of rows affected.
	 */
	public synchronized Integer update(HashMap<String, ArrayList<String>> list, Integer bookId) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return Integer Return the integer number of rows affected.
	 */
	public synchronized Integer delete(HashMap<String, ArrayList<String>> list, Integer bookId) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns a HashMap of all existing categories-subcategories (categories as keys, ArrayLists of 
	 * categories as values). 
	 * @param conn
	 * @return 				HashMap: categories as keys, ArrayLists of categories as values
	 */
	public synchronized HashMap<String, ArrayList<String>> findCategories(Connection conn) throws DAOException {
		HashMap<String, ArrayList<String>> categories = new HashMap<String, ArrayList<String>>();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{		
			pst = conn.prepareStatement(SELECT_CATEGORIES_SQL);
			rs = pst.executeQuery();
			while(rs.next()){
				categories.put(rs.getString("cat_category"), null);
			}
			rs.close();
			for(String k : categories.keySet()){
				pst = conn.prepareStatement(SELECT_SUBCATS_OF_CAT_SQL);
				pst.setString(1, k);
				ResultSet rset = pst.executeQuery();
				ArrayList<String> subcategories = new ArrayList<String>();
				while(rset.next()){
					subcategories.add(rset.getString("cat_subcategory"));
				}
				categories.put(k, subcategories);
				rset.close();
			}
		}catch(SQLException e){
			throw new DAOException("*Error retrieving categories. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		
		
		return categories;
	}

	/**
	 * Returns the pk id of the given category-subcategory if it exists, null otherwise. 
	 * @param category
	 * @param subcategory
	 * @param conn
	 * @return 			returns the (cat, subcat) id or null if the set does not exist.
	 */
	public synchronized Integer doesCatAndSubcatExist(String category, String subcategory,
			Connection conn) throws DAOException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Integer catId = null;
		
		try{
			pst = conn.prepareStatement(SELECT_CAT_SQL, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, category);
			pst.setString(2, subcategory);
			rs = pst.executeQuery();
			while(rs.next()){
				catId = rs.getInt("cat_id");
			}
			pst.close();
			rs.close();
		}catch(SQLException e){
			catId = null;
			throw new DAOException("*Error inserting categories. \n" + e.getMessage());
		}finally{
			try{
				if(pst != null)	pst.close();
				if(rs != null) rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return catId;
	}

	/**
	 * Inserts a set (bookId, catId) into the database, indicating a book belongs to the category-subcategory.
	 * @param bookId
	 * @param catId
	 * @param conn
	 * @return 				the number of rows affected
	 */
	public synchronized Integer insertBookCategoryKeys(Integer bookId, Integer catId, Connection conn) throws DAOException {
		PreparedStatement pst = null;
		
		Integer result = null;
		
		try{
			pst = conn.prepareStatement(INSERT_BOOK_CAT_SQL);
			pst.setInt(1, bookId);
			pst.setInt(2, catId);
			result = pst.executeUpdate();
			pst.close();
		}catch(SQLException e){
			throw new DAOException("*Error inserting categories. \n" + e.getMessage());
		}finally{		
			try{
				if(pst != null)	pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Removes all links between a book and it's category-subcategory, then checks for 
	 * cat-subcat not referencing a book.
	 * @param bookId
	 * @param conn
	 * @return 				returns the total number of rows affected by both statements
	 */
	public synchronized int deleteCategoryReferences(int bookId, Connection conn) throws DAOException{
		int rows = 0;
		
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		try{
			pst1 = conn.prepareStatement(DELETE_BOOKCAT_REFS_SQL);
			pst1.setInt(1, bookId);
			rows += pst1.executeUpdate();
			pst2 = conn.prepareStatement(DELETE_NULL_CAT_REFS_SQL);
			rows += pst2.executeUpdate();
			pst2.close();
			pst1.close();	
		}catch(SQLException e){
			throw new DAOException("Error deleteing category references. " + e.getMessage());
		}finally{
			try{
				if(pst1 != null) pst1.close();
				if(pst2 != null) pst2.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return rows;
		
	}
	
	/**
	 * Returns a HashMap  of categories-subcategories (cats as keys, ArrayList of subcats as values) to which
	 * a book belongs. 
	 * @param bookId	
	 * @param conn
	 * @return				a HashMap of categories-subcategories (cats as keys, array list of subcats) 
	 * 						to which a book belongs. 
	 */
	public synchronized HashMap<String, ArrayList<String>> getBooksCategories(int bookId, Connection conn) 
				throws DAOException{

		HashMap<String, ArrayList<String>> bookCats = new HashMap<String, ArrayList<String>>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(FIND_CATSSUBCATS_FORBOOK_SQL);
			pst.setInt(1, bookId);
			rs = pst.executeQuery();
			while(rs.next()){
				String cat = rs.getString("cat_category");
				if(!bookCats.containsKey(cat)){
					ArrayList<String> al = new ArrayList<String>();
					al.add(rs.getString("cat_subcategory"));
					bookCats.put(cat, al);
				}else{
					bookCats.get(cat).add(rs.getString("cat_subcategory"));
				}
			}
			rs.close();
			pst.close();
		}catch(SQLException e){
			throw new DAOException("Error getting books categories. " + e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return bookCats;
	}
	
	

}
