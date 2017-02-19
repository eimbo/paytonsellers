<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
function confirm_click(title){
return confirm("Are you sure you want to delete " + title + "?");
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
		<jsp:include page="../include/Header.jsp"/>
	</header>
	
	<aside class="adminnav">
		<a class="btn" href="${pageContext.request.contextPath}/admin/admin-front">Administrator</a>
		<br/>
		<a class="btn" href="${pageContext.request.contextPath}/admin/book-form/${book.book_id}">Edit Book</a>
		<br/>
		<a class="btn" href="${pageContext.request.contextPath}/admin/edit-categories/${book.book_id}">Edit Categories</a>
		<br/>
		<a class="btn" href="${pageContext.request.contextPath}/admin/delete-book/${book.book_id}" 
			onclick="return confirm_click('${book.book_title}');">Delete Book</a>
		<br/>
	</aside>

<div class="adminmain">
	
	<article id="info">
		<h2>${book.book_title}</h2>
		<img style="float:left;padding:0px 20px 20px 0px" src="http://boweblogic.com/paytonsellersbooks/img/${book.book_img}" width="130px" height="200px"/>
		<p>${book.book_author_first } ${book.book_author_last }</p>
		<p>$${book.book_price / 100 }</p>
		<p style="clear:left">${book.book_descript }</p>
	</article>
	
	<article id="catchecks">
		
		<c:forEach items="${bookCats}" var="bookCat">
			<div class="catinfo" >
				${bookCat.key}
			<hr/>
			
				<c:forEach items="${bookCat.value}" var="subcats">
				<c:forEach items="${subcats}" var="subcat">
					${subcat}<br/>
				</c:forEach>
				</c:forEach>
			</div>


		</c:forEach>
		
	</article>
</div>
</body>
</html>