/**
 * CookieHelper.java
 * 
 */

package com.paytonsellersbooks.utility;

public class CookieHelper {

	/**
	 * Returns the String id from the cookie or null if not a book cookie 
	 * (The cookie names are of the form 'paytonsellers_xxx' where x's 
	 * represent the book id.
	 */
	public String getIdFromCookie(String cookieName){
		String id = null; 
		if(cookieName.length() > 14){
			if(cookieName.substring(0, 14).equals("paytonsellers_")){	
				id = cookieName.substring(14, cookieName.length());
			}
		}
		
		return id;
	}
	
}
