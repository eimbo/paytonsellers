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

	<br/>
	<article class="featured">
		<div class="featuredtitle">Results</div>
			<c:forEach items="${books}" var="book">
				<a class="book" href="${pageContext.request.contextPath}/view-book/${book.book_id}" >
					<img src="http://localhost:8080/paytonsellersbooks/img/${book.book_img}" /><br/>
					<span class="booktitle">${book.book_title }</span><br/>
					${book.book_author_first }	${book.book_author_last }<br/>
					$${book.book_price / 100 }
				</a>
			</c:forEach>		
	</article>
	
	
<br/><br/>
</div> <!--  END MAIN -->
</body>
</html>
