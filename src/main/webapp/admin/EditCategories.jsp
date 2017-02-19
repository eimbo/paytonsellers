<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
		<jsp:include page="../include/Header.jsp"/>
	</header>
	
	<aside class="adminnav">
		<a class="btn" href="${pageContext.request.contextPath}/admin/view-book/${book.book_id}">View Book</a><br/>
	</aside>

<div class="adminmain">
	
	<article id="info">
		
		<h2>${book.book_title}</h2>
		<img src="http://boweblogic.com/paytonsellersbooks/img/${book.book_img}" width="130px" height="190px"/>
		<br/>
		${book.book_author_first} ${book.book_author_last}
		<br/>
		
		<p>${book.book_descript}...</p>
	</article>
	
	<article id="catchecks">
		<button class="btn" onclick="goBack()">Back</button>
		<hr/>
		
		<div style="width:650px; height:120px">
			<c:forEach items="${bookCats}" var="bookCat">
				<div class="catinfo" style="height:100px">
					${bookCat.key}
				<hr/>
					<c:forEach items="${bookCat.value}" var="subcats">
					<c:forEach items="${subcats}" var="subcat">
						${subcat}<br/>
					</c:forEach>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<br/>
		
		<hr/>
			
		
		<form action="${pageContext.request.contextPath}/admin/save-categories" method="post">
			<input class="btn" type="submit" value="Save"/>
				<input type="hidden" name="bookId" value="${book.book_id}">
				
				<div style="width:650px; height:120px">
				<c:forEach items="${categories}" var="category">
					<div class="catinfo">
					<b>${category.key}</b>
					<hr/>
					
						<c:forEach items="${category.value}" var="list">
						<c:forEach items="${list}" var="subcategory">
						 	<input type="checkbox" name="${category.key}[]" value="${subcategory}"> ${subcategory }<br/>
						</c:forEach>
						</c:forEach>
						<input type="text" name="${category.key}[]" /><br/>
						<input type="text" name="${category.key}[]" /><br/>
					</div>
				</c:forEach>
				
					<div class="catinfo">
					<b><input type="text" name="newCat1"/><br/></b>
					<hr/>
						<input type="text" name="newCategory1[]"/>
						<input type="text" name="newCategory1[]"/>
					</div>
					
					<div class="catinfo">
					<b><input type="text" name="newCat2"/><br/></b>
					<hr/>
						<input type="text" name="newCategory2[]"/>
						<input type="text" name="newCategory2[]"/>
					</div>
				</div>

				<button class="btn" onclick="goBack()">Back</button>
				<input class="btn" type="submit" value="Save"/>
				
		</form>
		<br/>
		<br/>
		<br/>
	</article>


	
</div>
</body>
</html>