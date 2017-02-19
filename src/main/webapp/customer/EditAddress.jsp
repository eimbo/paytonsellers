<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	
		<h3>Edit Your Shipping Address</h3>
		<p>${customer.cus_email}</p>
		
		<form action="${pageContext.request.contextPath}/customer/save-address" method="post">
			<fieldset>
			<legend><h2>Update Shipping Address</h2></legend>
			<input type="hidden" name="pmntProcess" value="${pmntProcess }"/>
			<input type="hidden" name="cusId" value="${customer.cus_id}"/>
			<p><label for="l1">Address Line 1</label>
			<input type="text" id="l1" name="add_l1" value="${customer.address.add_l1}"/><br/>
			</p>
			<p><label for="l2">Address Line 2</label>
			<input type="text" id="l2" name="add_l2" value="${customer.address.add_l2}"/><br/>
			</p>
			<p><label for="city">City</label>
			<input type="text" id="city" name="add_city" value="${customer.address.add_city}"/><br/>
			</p>
			<p><label for="state">State</label>
			<input type="text" id="state" name="add_state" value="${customer.address.add_state}"/><br/>
			</p>
			<p><label for="zip">Zip Code</label>
			<input type="text" id="zip" name="add_zip" value="${customer.address.add_zip}"/><br/>
			</p>
			<p><label for="submit">&nbsp</label>
			<input type="submit" id="submit" class="btn" value="Update"/>
			</p>
			</fieldset>
		</form>
		
		
	</div>
</body>
</html>
