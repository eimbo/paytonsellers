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
	


<div class="main" style="width:95%;margin:auto">

	<c:forEach items="${categories}" var="category">
		<div class="catinfo">
		<a id="navcat" href="${pageContext.request.contextPath}/category/${category.key}" >
			${category.key}
		</a>
		
		<hr/>

				<c:forEach items="${category.value}" var="list">
				<c:forEach items="${list }" var="subcategory">
					<a id="navsubcat" href="${pageContext.request.contextPath}/category/${category.key}/${subcategory}">${subcategory}</a><br/>
				</c:forEach>
				</c:forEach>  
		</div>

	</c:forEach>
	
</div>
</body>
</html>
