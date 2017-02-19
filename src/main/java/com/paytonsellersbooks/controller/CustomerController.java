/**
 * CustomerController.java
 * 
 */

package com.paytonsellersbooks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.paytonsellersbooks.action.CustomerAction;
import com.paytonsellersbooks.action.ViewAction;
import com.paytonsellersbooks.model.Address;
import com.paytonsellersbooks.model.Book;
import com.paytonsellersbooks.model.Customer;
import com.paytonsellersbooks.model.Invoice;
import com.paytonsellersbooks.model.InvoiceDetail;
import com.paytonsellersbooks.utility.CookieHelper;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {

	// customer/my-account
	/**
	 * Returns /customer/MyAccount with customer info, address and shopping cart, and invoices.
	 */
	@RequestMapping(value="/my-account")
	public String myAccount(HttpServletRequest request, Model model){
		
		int cusId = (Integer) request.getSession().getAttribute("cusid");

		CustomerAction customerAction = new CustomerAction();
		Customer customer = customerAction.getCustomerAndAddress(cusId);
		model.addAttribute("customer", customer);
		
		ViewAction viewAction = new ViewAction();
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
		
		
		
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		invoices = customerAction.getAllInvoices(cusId);
		model.addAttribute("invoices", invoices);

		return "/customer/MyAccount";
	}
	
	// customer/checkout
	/**
	 * Returns /customer/Shipping with customers address for verification.
	 */
	@RequestMapping(value="/checkout")
	public String checkout(Model model, HttpServletRequest request){
		int cusId = (Integer) request.getSession().getAttribute("cusid");
		
		// Get customer and address
		CustomerAction customerAction = new CustomerAction();
		Customer customer = customerAction.getCustomerAndAddress(cusId);

		model.addAttribute("customer", customer);
		return "/customer/Shipping";
	}
	
	// /customer/edit-address
	/**
	 * Returns /customer/EditAddress with customers address for updating.
	 */
	@RequestMapping(value="/edit-address")
	public String editAddress(Model model, HttpServletRequest request){
		String id = request.getParameter("cusId");
		int cusId = Integer.parseInt(id);
		String pmntProcess = request.getParameter("pmntProcess");
		model.addAttribute("pmntProcess", pmntProcess);
	
		// Get customer and address
		CustomerAction customerAction = new CustomerAction();
		Customer customer = customerAction.getCustomerAndAddress(cusId);
	
		model.addAttribute("customer", customer);
		
		return "/customer/EditAddress";
	}
	
	// /customer/save-address
	/**
	 * Returns a redirect to /customer/my-account or /customer/shipping after saving/updating address
	 * depending on if the customer was in checkout flow.
	 */
	@RequestMapping(value="/save-address")
	public String saveAddress(HttpServletRequest request, @ModelAttribute Address address){
		String id = request.getParameter("cusId");
		int cusId = Integer.parseInt(id);
		String pmntProcess = request.getParameter("pmntProcess");
		
		CustomerAction customerAction = new CustomerAction();
		
		address.setAdd_l1(request.getParameter("add_l1"));
		address.setAdd_l2(request.getParameter("add_l2"));
		address.setAdd_state(request.getParameter("add_state"));
		address.setAdd_zip(request.getParameter("add_zip"));
		
		customerAction.saveAddress(address, cusId);
		
		if(pmntProcess.equals("true")){ // was in checkout
			return "redirect:/customer/checkout";
		}
		
		return "redirect:/customer/my-account";
	}
	 
	// customer/payment
	/**
	 * Returns /customer/Payment to collect payment info (after user verifies their shipping address).
	 * Sends the current shopping cart, customer, address. 
	 */
	@RequestMapping(value="/payment")
	public String payment(HttpServletRequest request, Model model){
		String id = null;
		int cusId = 0;
		try{
			id = request.getParameter("cusId");
			cusId = Integer.parseInt(id);
		}catch(Exception e){
			return "redirect:/customer/my-account";
		}
		CustomerAction customerAction = new CustomerAction();
		Customer customer = customerAction.getCustomerAndAddress(cusId);
		model.addAttribute("customer", customer);
		
		ViewAction viewAction = new ViewAction();
		
		ArrayList<InvoiceDetail> cart = new ArrayList<InvoiceDetail>();
		int invLineCounter = 1;
		Cookie[] cookies = request.getCookies();
		CookieHelper helper = new CookieHelper();
		
		if (cookies != null) {
			for(int i=0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String bookId = helper.getIdFromCookie(cookie.getName());
				if(bookId != null){
					InvoiceDetail invdet = new InvoiceDetail();
					
					int d = Integer.parseInt(bookId);
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
			model.addAttribute("cart", cart);
			return "redirect:/view-cart";
		}
		model.addAttribute("cart", cart);
		
		return "/customer/Payment";
	}
	
	// customer/purchase
	/** 
	 * Returns a redirect to a view for the invoice of newly purchased items. This submits an order. A call to this 
	 * method will verify payment info and save the invoice, clear cookies. Redirect to an invoice view.
	 */
	@RequestMapping(value="/purchase")
	public String purchase(HttpServletRequest request, HttpServletResponse response){
		// Verify payment info
	
		
		// get customer info and address
		int cusId = (Integer) request.getSession().getAttribute("cusid");
		CustomerAction customerAction = new CustomerAction();
		Customer customer = customerAction.getCustomerAndAddress(cusId);
		
		// populate Invoice
		Invoice invoice = new Invoice();
		invoice.setInv_date(new Date());
		invoice.setInv_cus_first(customer.getCus_first());
		invoice.setInv_cus_last(customer.getCus_last());
		invoice.setInv_add_l1(customer.getAddress().getAdd_l1());
		invoice.setInv_add_l2(customer.getAddress().getAdd_l2());
		invoice.setInv_add_city(customer.getAddress().getAdd_city());
		invoice.setInv_add_city(customer.getAddress().getAdd_state());
		invoice.setInv_add_city(customer.getAddress().getAdd_zip());
		
		// Get cart
		ArrayList<InvoiceDetail> cart = new ArrayList<InvoiceDetail>();
		int invLineCounter = 1;
		Cookie[] cookies = request.getCookies();
		CookieHelper helper = new CookieHelper();
		
		ViewAction viewAction = new ViewAction();
		if (cookies != null) {
			for(Cookie cookie : cookies){
				String bookId = helper.getIdFromCookie(cookie.getName());
				if(bookId != null){
					InvoiceDetail invdet = new InvoiceDetail();
					
					int d = Integer.parseInt(bookId);
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
			return "redirect:/customer/my-account";
		}
		
		invoice = customerAction.saveInvoice(invoice, cart, customer.getCus_id());
		if(!(invoice.getInv_id() == 0)){
			for(Cookie cookie : cookies){
				String bookId = helper.getIdFromCookie(cookie.getName());
				if(bookId != null){
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		
		
		return "redirect:/customer/invoice/" + cusId + "/" + invoice.getInv_id() ;
	
	}
	
	// customer/invoice/{id}/{invid}
	/**
	 * Returns a view of an invoice. Sends an Invoice object and an array list of InvoiceDetail's 
	 * for the invoice.
	 */
	@RequestMapping(value="/invoice/{id}/{invid}")
	public String viewInvoice(@PathVariable int id, @PathVariable int invid, HttpServletRequest request, Model model){
		
		int cusId = (Integer) request.getSession().getAttribute("cusid");
		if(id != cusId){
			return "redirect:/my-account";
		}
		CustomerAction customerAction = new CustomerAction();
		Invoice invoice = customerAction.getInvoiceById(invid);
		model.addAttribute("invoice", invoice);
		
		ArrayList<InvoiceDetail> invoiceDetails = customerAction.getInvoiceDetailsByInvId(invid);
		model.addAttribute("invoiceDetails", invoiceDetails);

		
		return "/customer/Invoice";
	}
	
	/**
	 * Extra security and places 'cusid' in session when its missing.
	 */
	@ModelAttribute
	public void setCusId(HttpServletRequest request, HttpServletResponse response){
	
		try{ if( !(request.isUserInRole("user") || request.isUserInRole("admin")) ) 
			response.sendRedirect(request.getContextPath() + "/login");	
		} catch(IOException e){ 
			e.printStackTrace(); 
		}
		
		if(request.getSession().getAttribute("cusid") == null){
			CustomerAction customerAction = new CustomerAction();
			String email = request.getRemoteUser();
			int id = customerAction.getIdAtLogin(email);
			request.getSession().setAttribute("cusid", id);
		}
	}
}
