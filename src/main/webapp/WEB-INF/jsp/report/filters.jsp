<%@page import="com.bmat.digitalcharts.admin.controllers.ReportController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="report" name="itemMenuSeleccionado"/>
</jsp:include>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

	<form action='<c:url value="/report/create"/>' method="post">
	
		<h2>Year</h2>
		
		<div class="dropdown">
			<select name="year">
				<c:forEach items="${years}" var="year">
					<option value="${year}" ${year eq selectedYear ? "selected='selected'" : "" }>
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
					<option value="${country.id}" ${country.id eq selectedCountry ? "selected='selected'" : "" }>
						${country.name}
					</option>
				</c:forEach>
			</select>
		</div>
		
		<h2>Period of time</h2>
		
		<div id="month_week_selection">
		
			<div id="week_selection">
				<div class="select_button">Weekly report</div>
				
				<div class="dropdown">
					Week: 
					<select name="weekFrom">
						<c:forEach items="${weeks}" var="week">
							<option value="${week}" ${week eq selectedWeekFrom ? "selected='selected'" : "" }>
								${week}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div id="month_selection">
				<div class="select_button">Monthly report</div>
				
					<div class="dropdown">
					Month: 
						<select name="month">
							<c:forEach items="${months}" var="month">
								<option value="${month.id}" ${month.id eq selectedMonth ? "selected='selected'" : "" }>
									${month.name}
								</option>
							</c:forEach>
						</select>
					</div>
				
					<div class="dropdown">
					Week from: 
						<select name="weekFrom">
							<c:forEach items="${weeks}" var="week">
								<option value="${week}" ${week eq selectedWeekFrom ? "selected='selected'" : "" }>
									${week}
								</option>
							</c:forEach>
						</select>
					</div>
					
					
					<div class="dropdown">
					Week to:
						<select name="weekTo">
							<c:forEach items="${weeks}" var="week">
								<option value="${week}" ${week eq selectedWeekTo ? "selected='selected'" : "" }>
									${week}
								</option>
							</c:forEach>
						</select>
					</div>
			</div>
			
		</div>
		

		<h2>Right</h2>
		<div class="dropdown">
			<select name="right">
				<c:forEach items="${rights}" var="right">
					<option value="${right.id}" ${right.id eq selectedRight ? "selected='selected'" : "" }>
						${right.name}
					</option>
				</c:forEach>
			</select>
		</div>

		
		<div>
			<input type="submit" value="<%= ReportController.EXPORT_ACTION %>" name="action" />
		</div>
		
		<br/>
		<br/>
		
		<div id="archivar_reporte">
			<div class="msg">
				¿Desea dar este reporte por aprobado?
			</div>
			
			<div>
				<input type="submit" value="<%= ReportController.SAVE_ACTION %>" name="action"/>
			</div>
		</div>
		
	</form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
