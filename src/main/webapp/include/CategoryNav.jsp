<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<nav>		
	       	<span id="navtitle"><b>Categories</b></span><br/>
	
			<c:forEach items="${categories}" var="category">
				<a href="${pageContext.request.contextPath}/category/${category.key}">
					<span id="navcat">${category.key}</span>
				</a>
				<br/>

<%-- 				<c:forEach items="${category.value}" var="list">
					<c:forEach items="${list }" var="subcategory">
						<a href=""><span id="navsubcat">${ subcategory}</span></a><br/>
					</c:forEach>
					</c:forEach>  
--%>

			</c:forEach>

			<span id="navtitle"><b>Links</b></span><br/>  
		<div id="navcat">
			<a href="${pageContext.request.contextPath}/shipping-info">Shipping Info</a><br/>
			<a href="${pageContext.request.contextPath}/hours-and-location">Hours & Location</a><br/>
			<a href="${pageContext.request.contextPath}/support">Support</a><br/>
			<a href="${pageContext.request.contextPath}/terms-and-conditions">Terms and Conditions</a><br/>
		</div>

		</nav>