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
	
		<c:out value="${mssg}"/>		
		<form action='j_security_check' method="POST">
			<fieldset>
			<legend><h2>Login</h2></legend>
			
			<p>
	    	<label for="email">Email</label>
			<input type="text" id="email" name='j_username' placeholder="Email" size="30" tabindex="1"/>
			</p>
			
			<p>
	    	<label for="password">Password</label>
			<input type="password" id="password" name='j_password' placeholder="Password" size="30" tabindex="2"/>  
			</p> 	
	
			<p>
			<label for="submit">&nbsp</label>
			<input class="btn" id="submit" type="submit" value="Login" tabindex="3"/>
			<a class="btn" href="${pageContext.request.contextPath}/register">Register</a>
			</p>
		</form>
	</fieldset>
	
	
	
	
	
	

	</div>
</body>
</html>
