<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/site/icon.ico" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Payton Sellers</title>
	</head>
<body>
	<header>
		<jsp:include page="../include/Header.jsp"/>
	</header>
	
	<aside>
		<jsp:include page="../include/CategoryNav.jsp"/>	
	</aside>
	
	<div class="main">
		<h2>Payment Info</h2>
		
	
		${customer.cus_first } ${customer.cus_last }<br/>
		${customer.address.add_l1 }<br/>
		${customer.address.add_city }, 
		${customer.address.add_state }
		${customer.address.add_zip }
		<br/><br/>
		
		
	<style>
	table, th, td{
		text-align:left;
		padding:3px 10px;
		border-collapse:collapse;
	}
	</style>
	<table>
		<tr>
			<th>Line</th>
			<th>ID</th>
			<th>Title</th>
			<th>Author</th>
			<th>Price</th>
			<th>Qty</th>
			<th>Total</th>
		</tr>
	<c:forEach items="${cart}" var="invd">
		<tr>
			<td>${invd.invdet_line}. </td>
			<td>${invd.invdet_bookid}</td>
			<td>${invd.invdet_title} </td>
			<td>${invd.invdet_author} </td>
			<td>$${invd.invdet_price /100} </td>
			<td>${invd.invdet_units} </td>
			<td>$${(invd.invdet_price * invd.invdet_units) /100 }</td>
		</tr>
	</c:forEach>
	</table>
	<br/><br/>
	
	
	
		<form action="${pageContext.request.contextPath}/customer/purchase" method="post">
		<fieldset>
		<legend><h3>Payment Information</h3></legend>
			
			<p><label for="first">Name</label>
			<input type="text" id="first" name="first" placeholder="First Name" size="16"/>
			<input type="text" id="last" name="last" placeholder="Last Name" size="18"/>
			</p>
						
			
			
			<p><label for="add1">Address</label>
			<input type="text" id="add1" name="add1" placeholder="Payment Address" size="40"/>
			</p>

			<p><label for="city">City, State Zip</label>
			<input type="text" id="city" name="city" placeholder="City" size="15"/>
			<input type="text" id="state" name="state" placeholder="State" size="5"/>
			<input type="text" id="zip" name="zip" placeholder="Zip Code" size="8"/>
			</p>

			
			<p><label for="cctype">Card</label>
			<select id="cctype" name="cctype">
 					<option value="MasterCard" selected>MasterCard</option>
 					<option value="Visa">Visa</option>
 					<option value="AmericanExpress">AmericanExpress</option>
			</select>
			</p>
			
			<p><label for="ccnumber">Card Number</label>
			<input type="text" id="ccnumber" name="ccumber" placeholder="CC Number" size="30"/>
			</p>
			
			<p><label for="expiry">Expiration</label>
			<input type="text" id="expiry" name="expiry" placeholder="Expiry"/>
			<span style="font-family:Lucida Casual">code</span> <input type="text" id="code" name="code" placeholder="Code" size="5"/>
			</p>
			

			
			<p><label for="submit">&nbsp</label>
			<input class="btn" id="submit" type="submit" value="Submit Order"/>
			</p>
		</fieldset>
		</form>
		
		
	</div>
</body>
</html>
