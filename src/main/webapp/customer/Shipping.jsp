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
	
		<h2>Shipping Address</h2>
		<h4>Verify your shipping address.</h4>
	
		${customer.address.add_l1 }<br/>
		${customer.address.add_city },
		${customer.address.add_state}
		${customer.address.add_zip }
	
		<form action="${pageContext.request.contextPath}/customer/edit-address" method="post">
			<input type="hidden" name="cusId" value="${customer.cus_id }"/>
			<input type="hidden" name="pmntProcess" value="true"/>
			<input type="submit" class="btn" value="Edit Shipping"/>
		</form>
		<br/><br/>
		<form action="${pageContext.request.contextPath}/customer/payment" method="post">
			<input type="hidden" name="cusId" value="${customer.cus_id }"/>
			<input type="submit" class="btn" value="Continue"/>
		</form>
		
	</div>
</body>
</html>
