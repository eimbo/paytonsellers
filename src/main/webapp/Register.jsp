<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
	
	<c:out value="${error }"/>	
	<form:form commandName="customer" action="userregister" method="POST">
		<fieldset>
		<legend><h2>Become a member</h2></legend>
		
		<p>
		<label for="first">First Name</label>
		<form:input id="first" path="cus_first" placeholder="First Name" size="30" tabindex="1"/>
		</p>
		
		<p>
		<label for="last">Last Name</label>
		<form:input id="last" path="cus_last" placeholder="Last Name" size="30" tabindex="2"/>
    	</p>
    	
    	<p>
    	<label for="email">Email</label>
		<form:input id="email" path="cus_email" placeholder="Email" size="30" tabindex="3"/>
		</p>
		
		<p>
    	<label for="password">Password</label>
		<form:password id="password" path="cus_pass" placeholder="Password" size="30" tabindex="4"/>  
		</p>
		
		<p><label for="submit">&nbsp</label>
		<input class="btn" id="submit" type="submit" value="Sign Up" tabindex="5"/>
		 &nbsp &nbsp 
		 <span style="font-size:11pt;">Already a member?</span>
		<a class="btn" href="${pageContext.request.contextPath}/login">Login</a>
		
		</p>

		</fieldset>
	</form:form>
	
	
	
	
</div>
</body>
</html>


