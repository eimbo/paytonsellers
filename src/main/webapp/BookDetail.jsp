<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
function goBack() {
    window.history.back();
}
</script>
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
<!-- 
	<aside>
		<jsp:include page="include/CategoryNav.jsp"/>	
	</aside>
-->
<div class="main">

	
	<article style="width:75%;margin:auto; min-width:700px">
	
		<div id="smallnav"><a  href="" onclick="goBack()">Back</a></div>
	
		<c:if test="${pageContext.request.isUserInRole('admin')}">
			<a class="btn" href="${pageContext.request.contextPath}/admin/book-form/${book.book_id}">Edit Book</a>
			<a class="btn" href="${pageContext.request.contextPath }/admin/view-book/${book.book_id}">View Book</a>
		</c:if>
	
		
	<div style="width:400px;">	
		<div class="detailimg">
			<img src="http://localhost:8080/paytonsellersbooks/img/${book.book_img}" max-width="230px" max-height="300px"/><br/>
		</div>
	</div>
	
	<div style="width:800px">	
		<h2>${book.book_title}</h2>
		<p>${book.book_author_first } ${book.book_author_last }</p>
		<p>$${book.book_price / 100 }</p>
		
		
		
		<form action="${pageContext.request.contextPath}/add-to-cart" method="POST">
			<input type="hidden" name="bookId" value="${book.book_id}"/>
			<input type="submit" class="btn" value="Add To Cart"/>
			<select name="units">
  				<option value="1" selected>1</option>
  				<option value="2">2</option>
  				<option value="3">3</option>
  				<option value="4">4</option>
  				<option value="5">5</option>
			</select>
		</form>
		
		<div style="width:600px;float:right;"><p>${book.book_descript }</p></div>

	</div>
	
	
	</article>
	<article>
	</article>
</div>
</body>
</html>
