<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/account-style.css" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/site/icon.ico" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Payton Sellers</title>
	</head>
<body>
	<header>
		<jsp:include page="../include/Header.jsp"/>
	</header>

	
<div id="acct-main" >
	<style>
		
		
	</style>
	
	<div id="profilenav">
		<form action="${pageContext.request.contextPath}/customer/edit-address" method="post">
			<input type="hidden" name="pmntProcess" value="false"/>
			<input type="hidden" name="cusId" value="${customer.cus_id }">
			<input type="submit" class="btn" value="Update Shipping"/>
		</form>
		<a class="btn" href="#">Update Email</a>

	</div>
	
	<div id="profileinf"> 
				
		
		${customer.cus_email }<br/>
		Member Since <fmt:formatDate type="date" value="${customer.cus_date }" />
		<br/><br/>
		<h4>Shipping Information</h4>
		${customer.cus_first} ${customer.cus_last }<br/>
		${customer.address.add_l1 } 	
		<c:if test="${customer.address.add_l2 != null }">${customer.address.add_l2 }<br/></c:if>
		${customer.address.add_city }, ${customer.address.add_state } 
		${customer.address.add_zip }
		<br/><br/>
		


	</div>
	
	
	<a id="oinf" href="${pageContext.request.contextPath}/view-cart">
			<div id="oinftitle">Shopping Cart</div>
			<c:if test="${cart == null }">
				<h4>Your cart is empty.</h4>
			</c:if>
			<c:if test="${cart != null }">
				<style>
				table{
					//padding:3px 5px;
				}
				th, td{
					text-align:left;
					padding:3px 10px;
					border-collapse:collapse;
				}
				</style>
				<table>

				<c:forEach items="${cart}" var="invd">
					<tr style="height:40px">			
						<td>
						<div id="tit">${invd.invdet_title} </div></td>
						<td style="padding-left:10px;">${invd.invdet_units} at  </td>
						<td>$${invd.invdet_price /100}</td>
						
					</tr>
				</c:forEach>
				</table>
			</c:if>
			<br/><br/>
	
	
	</a>
	
	
	<div id="oinf" style="margin-top:15px;">
						<div id="oinftitle">Order History</div>
		<c:forEach items="${invoices}" var="invoice">
			<ul>
				<li><span style="color:#FFFFA0;text-decoration:underline">
				<a  href="${pageContext.request.contextPath}/customer/invoice/${customer.cus_id}/${invoice.inv_id}"> ${invoice.inv_id } </a> 
				</span>
				&nbsp &nbsp
				<fmt:formatDate type="date" value="${invoice.inv_date }" /></li>
			</ul>
		</c:forEach>
	</div>
	

</div>

</body>
</html>
