/**
 * AdminController.java
 * 
 */

package com.paytonsellersbooks.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paytonsellersbooks.action.AdminAction;
import com.paytonsellersbooks.action.ViewAction;
import com.paytonsellersbooks.model.Book;

@Controller
@RequestMapping(value="/admin")
public class AdminController { 

	
	// admin-front
	/**
	 * Returns the view for admin page. 
	 * 
	 */
	@RequestMapping(value="/admin-front")
	public String adminAdminFront(){
		return "/admin/AdminFront";
	}
	
	// book-form/{id}
	/**
	 * Returns the form to add or edit a book. If the path-variable is not 0, it will fetch
	 * the book from the database by it's id and add it to Spring's Model as an attribute. 
	 * Otherwise, a new Book is added as an attribute.
	 */
	@RequestMapping(value="/book-form/{id}")
	public String adminAddBook(@PathVariable int id, HttpServletRequest request, Model model){
		
		Book book = new Book();
		
		if(id != 0){ // get the book for editing
			AdminAction adminAction = new AdminAction();
			book = adminAction.getBookById(id);
			if(book.getBook_id() == 0 || book.getBook_title() == null){ // no book found
				model.addAttribute("error", "Book not found.");
				return "/admin/AdminFront";
			}
		}else{ 
			// set book_publisher, book_price to null to clear the form.
			book.setBook_pubyear(null);
			book.setBook_price(null);
		}
		model.addAttribute("book", book);
		return "/admin/BookForm";
	}
	
	// save-book
	/**
	 * Returns a redirect to /admin/view-book/{id} after saving the book in the database. 
	 * This method also stores an image uploaded from the form into a server directory.
	 */
	@RequestMapping(value="/save-book")
	public String adminSaveBook(@ModelAttribute Book book, Model model,	HttpServletResponse response, 
				RedirectAttributes redirectAttributes,
				@RequestParam MultipartFile file	){		
		
		// Validate book
		if(book == null || book.getBook_title() == null || book.getBook_title().equals("")
				|| book.getBook_author_last() == null || book.getBook_author_last().equals("")
				|| book.getBook_img() == null || book.getBook_img().equals("")
				|| book.getBook_isbn() == null || book.getBook_isbn().equals("")
				|| book.getBook_price() == null ){
			model.addAttribute("error", "Check input");
			model.addAttribute("book", book);
			return "/admin/BookForm";
		}

		// write the image in the server's /paytonsellers/img directory
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
			
				File dir = new File(rootPath + File.separator + "webapps" + File.separator + "paytonsellersbooks" + File.separator + "img");
				if (!dir.exists()){
					dir.mkdirs();
				}
				
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + book.getBook_img());
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// End: write image.
			
		AdminAction adminAction = new AdminAction();
		ViewAction viewAction = new ViewAction();
		
		if(book.getBook_id() == 0){ // id is zero because new book, insert new book
			book = adminAction.addBook(book);
			model.addAttribute("book", book);
			
			HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
			model.addAttribute("categories", categories);
			
			// return view to edit categories immediately for new book
			return "/admin/EditCategories";
		}else{
			adminAction.updateBook(book);
		}
		
		redirectAttributes.addFlashAttribute("book", book);
		return "redirect:/admin/view-book/" + book.getBook_id();	
	}

	// view-book/{id}
	/**
	 * Returns the admin's view-book page for the requested book.
	 */
	@RequestMapping(value="/view-book/{id}")
	public String adminViewBook(@PathVariable int id, Model model){
		
		AdminAction adminAction = new AdminAction();
		Book book = adminAction.getBookById(id);
		if(book.getBook_id() == 0 || book.getBook_title() == null){ // no book found
			model.addAttribute("error", "No book found.");
			return "/admin/AdminFront";
		}
		model.addAttribute("book", book);
		
		HashMap<String, ArrayList<String>> bookCats = adminAction.getBooksCategories(id);
		model.addAttribute("bookCats", bookCats);
	
		return "/admin/ViewBook";
	}
	
	// edit-categories/{id}
	/**
	 * Returns /admin/EditCategories with a new book or an existing one depending on the {id}.
	 * It sends the requested book by its id, the cats-subcats the book belongs to, and all existing 
	 * cats-subcats, and returns an edit-categories view.
	 */
	@RequestMapping(value="/edit-categories/{id}")
	public String adminEditCategories(@PathVariable int id, Model model, HttpServletRequest request){	
		
		AdminAction adminAction = new AdminAction();
		Book book = adminAction.getBookById(id);
		if(book.getBook_id() == 0 || book.getBook_title() == null){ // no book found
			model.addAttribute("error", "No book found.");
			return "/admin/AdminFront";
		}
		model.addAttribute("book", book);
		
		HashMap<String, ArrayList<String>> bookCats = adminAction.getBooksCategories(id);
		model.addAttribute("bookCats", bookCats);
		
		ViewAction viewAction = new ViewAction();
		HashMap<String, ArrayList<String>> categories = viewAction.getCategories();
		model.addAttribute("categories", categories);

		return "/admin/EditCategories";
	}

	// save-categories
	/**
	 * Returns a redirect to /admin/view-book/{id} after updating/saving the books cats-subcats.
	 * The cats-subcats are collected form html form and sent to admin action class as a parameter. 
	 * The purpose is to have no cat-subcats that do not reference a book, so the cats-subcats always 
	 * represent the books in the database. 		
	 */
	@RequestMapping(value="/save-categories")
	public String adminSaveCategories(HttpServletRequest request, RedirectAttributes redirectAttributes){
		String strBookId = null;
		Integer bookId = null;
		
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		// Get parameter names
		Enumeration<String> en = request.getParameterNames();
		// Get parameter values
		Map<String, String[]> ma = request.getParameterMap();
	
		// Get Book Id
		String[] bk = ma.get("bookId");
		strBookId = bk[0];
		try{
			bookId = Integer.parseInt(strBookId);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		// Get newCat1, newCat2
		String[] x = ma.get("newCat1");
		String nc1 = x[0];
		String[] y = ma.get("newCat2");
		String nc2 = y[0];
		
		
		// Set Enum and Map values into HashMap
		HashMap<String, String[]> hmap = new HashMap<String, String[]>();
		while(en.hasMoreElements()){
			String nextElt = en.nextElement();
			
			if(nextElt.equals("bookId")){
				continue;
			}else if(nextElt.equals("newCategory1[]")){
				hmap.put(nc1, ma.get("newCategory1[]"));
				continue;
			}else if(nextElt.equals("newCategory2[]")){
				hmap.put(nc2, ma.get("newCategory2[]"));
				continue;
			}else{
			
			int len = nextElt.length();
			String newKey = null;
				if(len > 2){
					if(nextElt.charAt(len-1) == ']' && nextElt.charAt(len-2) == '[')
						newKey = nextElt.substring(0,  len-2); 
				}else{
					newKey = nextElt;
				}
			hmap.put(newKey, ma.get(nextElt));
			}
		}
		hmap.remove(null);

		//change values from String[] to ArrayList<String>
		for(String key : hmap.keySet()){
			boolean flag = false;
			ArrayList<String> al = new ArrayList<String>();
			String[] s = hmap.get(key);
		
			for(int i = 0; i < s.length; i++){
				if(s[i] != ""){
					al.add(s[i]);
					flag = true;
				}
			}
			if(flag) map.put(key, al);
		}

		// map print test
		for(String key : map.keySet()){
			System.out.print(key + ": ");
			for(String s : map.get(key))
				System.out.print("(" + s + ")");
			System.out.println("");
		}
		
		
		AdminAction adminAction = new AdminAction();
		
		Integer result = adminAction.setCategories(map, bookId);
		if(result < 0){
			return "redirect:/admin/admin-front";
		}
		 
		return "redirect:/admin/view-book/" + bookId ;
	}

	// delete-book/{id}
	/**
	 * Returns /admin/AdminFront after deleting a book from the system.
	 */
	@RequestMapping(value="/delete-book/{id}")
	public String adminDeleteBook(@PathVariable int id, Model model){
		AdminAction adminAction = new AdminAction();
		adminAction.removeBook(id);
		return "/admin/AdminFront";
	}

	// called before req-handling methods are invoked
	/**
	 * This method is called before any request-handling methods (@ModelAttribute on method)
	 * and ensures user in 'admin' role. Extra security. 
	 */
	@ModelAttribute
	public void checkUserRole(HttpServletRequest request, HttpServletResponse response){

		try{ if(!request.isUserInRole("admin")) response.sendRedirect(request.getContextPath() + "/home");	}
			catch(IOException e){ e.printStackTrace(); }
	}
	
	/**
	 * Converter for Spring's form-tags. String to Date for the Book object.
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
}
