<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	
	<div class="userbar">	
			<c:if test="${pageContext.request.isUserInRole('admin')}">
				<a href="${pageContext.request.contextPath}/admin/admin-front">Administrator</a>
			</c:if>
			<c:if test="${pageContext.request.isUserInRole('user') }">
				<a href="${pageContext.request.contextPath}/customer/my-account">${pageContext.request.getRemoteUser() }</a>
			</c:if>
			<a href="${pageContext.request.contextPath}/customer/my-account">My Account</a>
			<c:choose>
				<c:when test="${pageContext.request.isUserInRole('admin') || pageContext.request.isUserInRole('user') }">
				<a href="${pageContext.request.contextPath}/logout">Logout</a>
				</c:when>
				<c:otherwise>
				<a href="${pageContext.request.contextPath}/userlogin">Login</a>
				<a href="${pageContext.request.contextPath}/register">Register</a>
				</c:otherwise>
			</c:choose>
			<a href="${pageContext.request.contextPath}/view-cart">Cart</a>
	</div><!--  END USERBAR -->
	
	<a href="${pageContext.request.contextPath}/home">
		<div id="site"> Payton <span>Sellers</span> <small>booksellers since 1994</small></div>	
	</a>
	
	<hr/>
	
	<div id="navbar">
		<form action="${pageContext.request.contextPath }/search" method="get">
			<a href="${pageContext.request.contextPath}/all-categories">All Categories</a>
			<a href="${pageContext.request.contextPath}/category/Textbooks">Textbooks</a>
			<a href="${pageContext.request.contextPath}/category/Sci-Fi%20&%20Fantasy">Sci-Fi & Fantasy</a>
			<a href="${pageContext.request.contextPath}/category/Literature%20&%20Fiction">Literature & Fiction</a>
			<input type="text" style="font-size:.8em" id="search" name="q" placeholder=" Search for books..."/>
		</form>
	
	</div>
	<hr/>
	
	
