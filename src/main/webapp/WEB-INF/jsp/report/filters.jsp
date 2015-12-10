<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="report" name="itemMenuSeleccionado"/>
</jsp:include>


	<form action='<c:url value="/report/export"/>' method="post">
	
		<h2>Year</h2>
		
		<div class="dropdown">
			<select name="year">
				<c:forEach items="${years}" var="year">
					<option value="${year}">
						${year}
					</option>
				</c:forEach>
			</select>
		</div>
	
		<h2>Country</h2>
		
		<div class="dropdown">
			<select name="country">
<!-- 				<option value="">Select...</option> -->
				<c:forEach items="${countries}" var="country">
					<option value="${country.id}">
						${country.name}
					</option>
				</c:forEach>
			</select>
		</div>
		
		<h2>Weeks</h2>
		
		<div class="dropdown">
		From: 
			<select name="weekFrom">
				<c:forEach items="${weeks}" var="week">
					<option value="${week}">
						${week}
					</option>
				</c:forEach>
			</select>
		</div>
		
		
		<div class="dropdown">
		To:
			<select name="weekTo">
				<option value="">Select...</option>
				<c:forEach items="${weeks}" var="week">
					<option value="${week}">
						${week}
					</option>
				</c:forEach>
			</select>
		</div>
		

		<h2>Right</h2>
		<div class="dropdown">
			<select name="right">
				<c:forEach items="${rights}" var="right">
					<option value="${right.id}">
						${right.name}
					</option>
				</c:forEach>
			</select>
		</div>

		
		
		<div>
			<input type="submit" value="Generar reporte" />
		</div>
		
	</form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
