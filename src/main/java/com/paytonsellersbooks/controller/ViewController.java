/**
 * ViewController.java
 * 
 */

package com.paytonsellersbooks.controller;
 
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paytonsellersbooks.action.CustomerAction;
import com.paytonsellersbooks.action.ViewAction;
import com.paytonsellersbooks.model.Book;
import com.paytonsellersbooks.model.Customer;
import com.paytonsellersbooks.model.InvoiceDetail;
import com.paytonsellersbooks.utility.CookieHelper;

@Controller
public class ViewController {
	
	// /home
	/**
	 * Returns /Home with best selling in 'lit fic', 'sci-fi fantasy', and up to 30 books. 
	 */
	@RequestMapping(value="/home")
	public String home(Model model, HttpServletRequest request){
		ViewAction viewAction = new ViewAction();
		
		// Temp. Get up to 30 books 
		ArrayList<Book> books = new ArrayList<Book>();
		books = viewAction.getAllBooks();
		model.addAttribute("books", books);
		
		ArrayList<Book> litAndFic = viewAction.getBestSelling("Literature & Fiction");
		model.addAttribute("litAndFic", litAndFic);
		ArrayList<Book> sciFi = viewAction.getBestSelling("Sci-Fi & Fantasy");
		model.addAttribute("sciFi", sciFi);
		// Get categories
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);
		return "/Home";
	}
	
	// /view-cart
	/**
	 * Returns /Cart with all cart items in an array list as InvoiceDetail objects. Sends all categories.
	 */
	@RequestMapping(value="/view-cart")
	public String viewCart(Model model, HttpServletRequest request){
		// Get  existing categories
		ViewAction viewAction = new ViewAction();
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);
		
		ArrayList<InvoiceDetail> cart = new ArrayList<InvoiceDetail>();

		int invLineCounter = 1;
		Cookie[] cookies = request.getCookies();		
		CookieHelper helper = new CookieHelper();
		if (cookies != null) {
			for(int i=0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String id = helper.getIdFromCookie(cookie.getName());
				if(id != null){
					InvoiceDetail invdet = new InvoiceDetail();
					
					int d = Integer.parseInt(id);
					Book b = viewAction.getBookById(d);
					if(b.getBook_id() != 0){
						invdet.setInvdet_bookid(b.getBook_id());
						invdet.setInvdet_title(b.getBook_title());
						invdet.setInvdet_author(b.getBook_author_first() + " " + b.getBook_author_last());
						invdet.setInvdet_isbn(b.getBook_isbn());
						invdet.setInvdet_line(invLineCounter);
							invLineCounter++;
						invdet.setInvdet_price(b.getBook_price());
						int units = Integer.parseInt(cookie.getValue());
						invdet.setInvdet_units(units);
						
						cart.add(invdet);
					}
				}
				 
			}
		}
		if(cart.isEmpty()){
			cart = null;
		}
		model.addAttribute("cart", cart);
		
		// If they're signed show their info in the cart.
		CustomerAction customerAction = new CustomerAction();
		Customer customer = new Customer();
		try{
			int cusId = (Integer) request.getSession().getAttribute("cusid");
			customer = customerAction.getCustomerAndAddress(cusId);
		}catch(NullPointerException e){
			// e.printStackTrace();
		}
		model.addAttribute("customer", customer);

		
		return "Cart";
	}
	
	// /view-book/{id}
	/**
	 * Returns /BookDetail for a detail-view of a book. Sends book and existing categories.
	 */
	@RequestMapping(value="/view-book/{id}")
	public String viewBook(Model model, @PathVariable Integer id){
		// Get categories
		ViewAction viewAction = new ViewAction();
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);
		
		Book book = viewAction.getBookById(id);
		model.addAttribute("book", book);
		
		return "/BookDetail";
	}
	
	// /category/{category}
	/**
	 * Returns /Category with best selling, new books from that category. Sends the subcategories for sidenav.
	 */
	@RequestMapping(value="/category/{category}")
	public String category(@PathVariable String category, Model model, HttpServletRequest request){
		model.addAttribute("category", category); // category is simply a String.
		
		ViewAction viewAction = new ViewAction();
		ArrayList<Book> bestSelling = viewAction.getBestSelling(category);
		ArrayList<Book> newArrivals = viewAction.getNewArrivals(category);
		model.addAttribute("bestSelling", bestSelling);
		model.addAttribute("newArrivals", newArrivals);
		
		
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		ArrayList<String> subcats = new ArrayList<String>();
		for(String key : categories.keySet()){ // gets subcats from the HashMap above
			if(key.equals(category)){
				subcats = categories.get(key);
			}
		}
		model.addAttribute("subcats", subcats);
		
		return "/Category";
	}

	// /category/{category}/{subcategory}
	/**
	 * Returns /Subcategory with books from the subcategory and list of subcategories for sidenav.
	 */
	@RequestMapping(value="/category/{category}/{subcategory}")
	public String subcategory(@PathVariable String category, @PathVariable String subcategory, 
			HttpServletRequest request, Model model){
		
		model.addAttribute("category", category); // category is simply a String
		model.addAttribute("subcategory", subcategory); // simply a String for the view
	
		ViewAction viewAction = new ViewAction();
		ArrayList<Book> books = viewAction.getBooksInCatSubcat(category, subcategory);
		model.addAttribute("books", books);
		
		
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		ArrayList<String> subcats = new ArrayList<String>();
		for(String key : categories.keySet()){ // gets subcats from the HashMap above.
			if(key.equals(category)){
				subcats = categories.get(key);
			}
		}
		model.addAttribute("subcats", subcats);

		return "/Subcategory";
	}
	
	// /all-categories
	/** 
	 * Gets existing cats-subcats for the all categories view page.
	 */
	@RequestMapping(value="/all-categories")
	public String categories(Model model){
		// Get existing categories
		ViewAction viewAction = new ViewAction();
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);
		
		return "/Categories";
	}
	
	// /search
	/**
	 * Implements the search case.
	 */
	@RequestMapping(value="/search")
	public String search(Model model, HttpServletRequest req, HttpServletResponse res){
		String query = (String) req.getParameter("q");
		
		// Get categories
		ViewAction viewAction = new ViewAction();
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);
		
		ArrayList<Book> books = viewAction.getBooksFromSearchQuery(query);
		model.addAttribute("books", books);

		return "/Results";
	} 
	
	// /add-to-cart
	/**
	 * Returns a redirect to /view-cart with the cookie added to the response. The book_id is stored in the cookie name - "paytonsellers_XXX". 
	 * The quantity of books is the value of the cookie.
	 */
	@RequestMapping(value="/add-to-cart", method=RequestMethod.POST)
	public String addToCart(Model model, HttpServletRequest request, HttpServletResponse response ){
		String bookId = (String) request.getParameter("bookId");
		String qty = (String) request.getParameter("units");
		
		Cookie book = new Cookie("paytonsellers_" + bookId, qty);
		book.setMaxAge(60*60*24*365); // 1 year
		book.setPath("/");
		response.addCookie(book);
		return  "redirect:/view-cart";
	}
	
	// /update-cart
	/**
	 * Sends redirect after removing a book from the cart.
	 */
	@RequestMapping(value="/update-cart", method=RequestMethod.POST)
	public String removeBook(HttpServletRequest request, HttpServletResponse response){
		
		String bookId = request.getParameter("bookId");
		
		Cookie[] cookies = request.getCookies();
		CookieHelper helper = new CookieHelper();
		if(cookies != null){
			for(Cookie cookie : cookies){
				String id = helper.getIdFromCookie(cookie.getName());
				if(id != null){
					if(id.equals(bookId)){
						cookie.setValue(null);
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
			}
		}
		return "redirect:/view-cart";
	}
	
}

