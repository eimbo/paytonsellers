/**
 * Customer.java
 * 
 */

package com.paytonsellersbooks.model;

import java.util.ArrayList;
import java.util.Date;

public class Customer {
	
	private ArrayList<Invoice> invoice = new ArrayList<Invoice>();
	private int cus_id;
	private String cus_first;
	private String cus_last;
	private String cus_email;
	private Address address;
	private String cus_pass;
	private Date cus_date;
	
	
	/* Getters and Setters */
	public ArrayList<Invoice> getInvoice() {
		return invoice;
	}
	public void setInvoice(ArrayList<Invoice> invoice) {
		this.invoice = invoice;
	}
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public String getCus_first() {
		return cus_first;
	}
	public void setCus_first(String cus_first) {
		this.cus_first = cus_first;
	}
	public String getCus_last() {
		return cus_last;
	}
	public void setCus_last(String cus_last) {
		this.cus_last = cus_last;
	}
	public String getCus_email() {
		return cus_email;
	}
	public void setCus_email(String cus_email) {
		this.cus_email = cus_email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getCus_pass() {
		return cus_pass;
	}
	public void setCus_pass(String cus_pass) {
		this.cus_pass = cus_pass;
	}
	public Date getCus_date() {
		return cus_date;
	}
	public void setCus_date(Date cus_date) {
		this.cus_date = cus_date;
	}
	

	
}
