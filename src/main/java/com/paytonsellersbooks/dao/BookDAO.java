package com.paytonsellersbooks.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.paytonsellersbooks.model.Book;

public interface BookDAO {
	
	int insert(Book book, Connection conn) throws DAOException;
	int update(Book book, Connection conn) throws DAOException;
	int delete(int bookId, Connection conn) throws DAOException;
	Book findBookById(int bookId, Connection conn) throws DAOException;
	
	ArrayList<Book> findBooksByCategory(String category, Connection conn) throws DAOException;
	ArrayList<Book> findBestSelling(String category, Connection conn)throws DAOException;
	ArrayList<Book> findNewArrivals(String category, Connection conn)throws DAOException;
	ArrayList<Book> findBooksInCatSubcat(String category, String subcategory, Connection conn) throws DAOException;
	ArrayList<Book> findAllBooks(Connection conn) throws DAOException;
	ArrayList<Book> findBooksFromQuery(String query, Connection conn) throws DAOException;
}
