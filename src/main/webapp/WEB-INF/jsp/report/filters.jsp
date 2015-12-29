<%@page import="com.bmat.digitalcharts.admin.controllers.ReportController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="report" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/report_filters.js" />' ></script>

	<div class="msg" id="main-msg">${msg}</div>
	
	<input type="hidden" id="form_action" value='<c:url value="/report/create"/>' />
	<input type="hidden" id="enable_save_url" value='<c:url value="/report/isReady"/>' />
	<input type="hidden" id="isMonthlyReport" value="${selectedIsMonthly}" />

	<form method="post" id="form">
	
		<input type="hidden" id="action" name="action" />
		
		<div id="crear_reporte">
	
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
					<c:forEach items="${countries}" var="country">
						<option value="${country.id}" ${country.id eq selectedCountry ? "selected='selected'" : "" }>
							${country.name}
						</option>
					</c:forEach>
				</select>
			</div>
			
			<h2>Period of time</h2>
			
			<div id="month_week_selection">
			
				<div id="week_selection" class="${selectedIsMonthly == true ? 'disabled-selection' : '' }">
					<div class="select_button" onclick="activateWeekSelection()">Weekly report</div>
					
					<div class="selection_content">
						<div class="dropdown">
							Week: 
							<select name="weekFrom">
								<option value="">Select...</option>
								<c:forEach items="${weeks}" var="week">
									<option value="${week}" ${week eq selectedWeekFrom ? "selected='selected'" : "" }>
										${week}
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div id="month_selection" 
					class="${selectedIsMonthly == null || selectedIsMonthly == false ? 'disabled-selection' : '' }">
					<div class="select_button" onclick="activateMonthSelection()">Monthly report</div>
					
					<div class="selection_content">
					
						<div class="dropdown">
						Month: 
							<select name="month">
								<option value="">Select...</option>
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
								<option value="">Select...</option>
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
								<option value="">Select...</option>
								<c:forEach items="${weeks}" var="week">
									<option value="${week}" ${week eq selectedWeekTo ? "selected='selected'" : "" }>
										${week}
									</option>
								</c:forEach>
							</select>
						</div>
						
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
			
			
			
			<h2>DCP</h2>
			<div class="dropdown">
				<select name="source">
					<option value="">Select...</option>
					<c:forEach items="${sources}" var="source">
						<option value="${source.id}" ${source.id eq selectedSource ? "selected='selected'" : "" }>
							${source.name}
						</option>
					</c:forEach>
				</select>
			</div>
	
			
			<div>
				<input type="button" value="<%= ReportController.EXPORT_ACTION %>" onclick="validateForm(this)" />
			</div>
			
		</div>
		
		<br/>
		<br/>
		
		<div id="archivar_reporte" style="display: none;">
			<div class="msg">
				¿Desea dar este reporte por aprobado?
			</div>
			
			<div>
				<input type="button" value="<%= ReportController.SAVE_ACTION %>" onclick="saveReport(this)"
					disabled="disabled" id="save_button"/>
					
				<input type="button" value="Cancelar" onclick="clearSelection()"/>
			</div>
		</div>
		
	</form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
