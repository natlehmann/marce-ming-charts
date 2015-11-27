<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />


	<form action='<c:url value="/report/export"/>' method="post">
	
		<h2>Country</h2>
		
		<div class="dropdown">
			<select name="country">
<!-- 				<option value="">Select...</option> -->
				<c:forEach items="${countries}" var="country">
					<option value="${country.id}" ${selectedCountry eq country.id ? "selected='selected'" : ""}>
						${country.name}
					</option>
				</c:forEach>
			</select>
		</div>


		
		
		<div>
			<input type="submit" value="Generar reporte" />
		</div>
		
	</form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
