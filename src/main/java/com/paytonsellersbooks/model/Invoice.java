/**
 * Invoice.java
 * 
 */

package com.paytonsellersbooks.model;

import java.util.ArrayList;
import java.util.Date;

public class Invoice {
	 
	private ArrayList<InvoiceDetail> invoice_detail = new ArrayList<InvoiceDetail>();
	private int inv_id;
	private Date inv_date;
	private String inv_cus_first;
	private String inv_cus_last;
	private String inv_add_l1;
	private String inv_add_l2;
	private String inv_add_city;
	private String inv_add_state;
	private String inv_add_zip;

	
	@Override
	public String toString() {
		return "Invoice [inv_id=" + inv_id + ", inv_date=" + inv_date + ", inv_cus_first=" + inv_cus_first
				+ ", inv_cus_last=" + inv_cus_last + "]";
	}
	/* Getters and Setters */
	public int getInv_id() {
		return inv_id;
	}
	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}
	public Date getInv_date() {
		return inv_date;
	}
	public void setInv_date(Date inv_date) {
		this.inv_date = inv_date;
	}
	public String getInv_cus_first() {
		return inv_cus_first;
	}
	public void setInv_cus_first(String inv_cus_first) {
		this.inv_cus_first = inv_cus_first;
	}
	public String getInv_cus_last() {
		return inv_cus_last;
	}
	public void setInv_cus_last(String inv_cus_last) {
		this.inv_cus_last = inv_cus_last;
	}
	public String getInv_add_l1() {
		return inv_add_l1;
	}
	public void setInv_add_l1(String inv_add_l1) {
		this.inv_add_l1 = inv_add_l1;
	}
	public String getInv_add_l2() {
		return inv_add_l2;
	}
	public void setInv_add_l2(String inv_add_l2) {
		this.inv_add_l2 = inv_add_l2;
	}
	public String getInv_add_city() {
		return inv_add_city;
	}
	public void setInv_add_city(String inv_add_city) {
		this.inv_add_city = inv_add_city;
	}
	public String getInv_add_state() {
		return inv_add_state;
	}
	public void setInv_add_state(String inv_add_state) {
		this.inv_add_state = inv_add_state;
	}
	public String getInv_add_zip() {
		return inv_add_zip;
	}
	public void setInv_add_zip(String inv_add_zip) {
		this.inv_add_zip = inv_add_zip;
	}
	public ArrayList<InvoiceDetail> getInvoice_detail() {
		return invoice_detail;
	}
	public void setInvoice_detail(ArrayList<InvoiceDetail> invoice_detail) {
		this.invoice_detail = invoice_detail;
	}
	
}
