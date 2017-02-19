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
		<jsp:include page="include/Header.jsp"/>
	</header>
	
	<aside>
		<jsp:include page="include/CategoryNav.jsp"/>	
	</aside>
	
	<div class="main">
	
	<h2>Shopping Cart</h2>

		<c:if test="${(	pageContext.request.isUserInRole('user') ||
						pageContext.request.isUserInRole('admin') ) &&
						customer.cus_email != null }">
			<p><span style="color:#FFFFC0">You are signed in as ${customer.cus_email}</span></p>
		</c:if>

	<c:if test="${cart == null }">
		<h4>Your cart is empty.</h4>
	</c:if>
	<c:if test="${cart != null }">
	<style>
	table, th, td{
		text-align:left;
		padding:3px 10px;
		border-collapse:collapse;
	}
	</style>
	<table>
		<tr>
			<th> </th>
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
			<td>
		
			<form action="${pageContext.request.contextPath}/update-cart" method="post">
				<input type="hidden" name="bookId" value="${invd.invdet_bookid }"/>
				<input type="submit" class="btn" value="Remove"/>
			</form>
				</td>
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
	</c:if>
	<br/><br/>
	
	<a class="btn" href="${pageContext.request.contextPath}/customer/checkout">Checkout</a>

	</div>
</body>
</html>
