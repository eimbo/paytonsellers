<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

	<a class="btn" href="${pageContext.request.contextPath}/customer/my-account">My Account</a>
		
	<div class="main" style="width:70%; margin:auto; color:black; padding-left:30px;background:white;">
		<!--  format invoice -->
	
	
		<h2>Invoice</h2>
		Invoice Number: ${invoice.inv_id}<br/>
		Date: <fmt:formatDate type="date" value="${invoice.inv_date }" /><br/><br/>
		${invoice.inv_cus_first } ${invoice.inv_cus_last }<br/>
		${invoice.inv_add_l1 }<br/>
		${invoice.inv_add_city }, ${invoice.inv_add_state} ${invoice.inv_add_zip}
		<br/><br/>
		<hr/>
		
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
		
	<c:forEach items="${invoiceDetails}" var="invd">
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
	
		

		
	</div>
</body>
</html>
