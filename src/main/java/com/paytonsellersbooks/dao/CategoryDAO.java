/**
 * CategoryDAO.java
 * 
 */

package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public interface CategoryDAO {
	
	Integer insert(String category, String subcategory, Connection conn) throws DAOException;
	
	Integer update(HashMap<String, ArrayList<String>> list, Integer bookId) throws DAOException;
	Integer delete(HashMap<String, ArrayList<String>> list, Integer bookId) throws DAOException;
	
	HashMap<String,ArrayList<String>> findCategories(Connection conn) throws DAOException;
	Integer insertBookCategoryKeys(Integer bookId, Integer catId, Connection conn) throws DAOException;
	Integer doesCatAndSubcatExist(String category, String subcategory, Connection conn) throws DAOException;

	int deleteCategoryReferences(int bookId, Connection conn) throws DAOException;
	HashMap<String, ArrayList<String>> getBooksCategories(int bookId, Connection conn) throws DAOException;
	
}
	

