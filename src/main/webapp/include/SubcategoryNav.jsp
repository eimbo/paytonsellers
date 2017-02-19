<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<nav>		
	       	<span id="navtitle"><b>${category }</b></span><br/>
	
			<c:forEach items="${subcats}" var="subcat">	
				<a href="${pageContext.request.contextPath}/category/${category}/${subcat}">
					<span id="navsubcat">${subcat}</span>
					</a>
				<br/>
			</c:forEach>
		</nav>