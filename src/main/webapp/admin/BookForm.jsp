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
		<jsp:include page="../include/Header.jsp"/>
	</header>
	
	<aside>
		<jsp:include page="AdminNav.jsp"/>	
	</aside>

<div class="main">

	<h3 style="color:red"><c:out value="${error}"/></h3>
	<form:form commandName="book" action="${pageContext.request.contextPath}/admin/save-book" method="post" 
		enctype="multipart/form-data">
		<fieldset>
		<legend><h2>Add Book</h2></legend>
    		<form:hidden path="book_id" />
    	<p>
    		<label for="title">Title</label>
			<form:input id="title" path="book_title" placeholder=" Title" size="60"  tabindex="1"/>	
		</p>
		
		<p>
    		<label for="author">Author</label>
			<form:input id="author" path="book_author_first" placeholder=" First Name" size="25"  tabindex="2"/> 	
			<form:input id="aurhot" path="book_author_last" placeholder=" Last Name" size="29"  tabindex="3"/>	
		</p>
		
		<p>	
    		<label for="price">Price (cents, no decimals)</label>
			<form:input id="price" path="book_price" placeholder=" $4.99  equals  499 " size="60"  tabindex="4"/>	
		</p>
		
		<p>
    		<label for="image">Image</label>
			<form:input id="image" path="book_img" placeholder=" Image" size="60"  tabindex="5"/>	
		</p>
		
	 <p>	<label for="file">Upload Image</label>
			<input type="file" id="file" name="file" size="60" />	</p>
		
		<p>	
    		<label for="published">Published</label>
			<form:input id="published" path="book_pubyear" placeholder=" Year" size="20" tabindex="6" />	
			<form:input id="published" path="book_publisher" placeholder=" Publisher" size="34" tabindex="7" />	
		</p>
		
		<p>	
    		<label for="isbn">ISBN</label>
			<form:input id="isbn" path="book_isbn" placeholder=" Isbn" size="60" tabindex="8" />	
		</p>
		
		<p>	
    		<label for="length">Length</label>
			<form:input id="length" path="book_length" placeholder=" Length" size="60" tabindex="9" />	
		</p>
		
		<p>	
    		<label for="dim">Dimensions</label>
			<form:input id="dim" path="book_dim" placeholder=" Dimensions" size="60" tabindex="10" />
		</p>
		
		<p>	
    		<label for="format">Format</label>
			<form:input id="format" path="book_format" placeholder=" Format" size="60" tabindex="11" />	
		</p>
		
<%--     	
			<label for="buycount">Buy Counter</label>
			<form:input id="buycount" path="book_buycounter" placeholder=" Buy Counter" size="60" />
    		<label for="viewcount">View Counter</label>
			<form:input id="viewcount" path="book_viewcounter" placeholder=" View Counter" size="60" />
--%>
		
		<p>	
    		<label for="date">Arrival Date</label>
			<form:input id="date" path="book_date" placeholder=" MM/DD/YYYY" size="60" tabindex="12" />	
		</p>
		
		<p>	
			<label for="descript">Description</label>
			<form:textarea id="descript" path="book_descript"  rows="5" cols="61" tabindex="13"/>	
		</p>
		
		<p>
			<label for="submit">&nbsp</label>
			<input class="btn" type="submit" id="submit" value="Save Book" tabindex="14"/>
		</p>
		
		</fieldset>
	</form:form>
	
</div>
</body>
</html>