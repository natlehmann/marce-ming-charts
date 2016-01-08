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
		
			<div id="busca">         	
            	<div class="izq"><img src='<c:url value="/images/h1Izq.jpg"/>' width="14" height="34" /></div>
 				<h1>GENERAR REPORTE</h1> 
            	<div class="der"><img src='<c:url value="/images/h1Der.jpg"/>' width="31" height="34" /></div>
	
				<h2>Year</h2>
				
				<div class="dropdown">
					<select name="year" class="dropdown-select">
						<c:forEach items="${years}" var="year">
							<option value="${year}" ${year eq selectedYear ? "selected='selected'" : "" }>
								${year}
							</option>
						</c:forEach>
					</select>
				</div>
		
				<h2>Country</h2>
				
				<div class="dropdown">
					<select name="country" class="dropdown-select">
						<c:forEach items="${countries}" var="country">
							<option value="${country.id}" ${country.id eq selectedCountry ? "selected='selected'" : "" }>
								${country.name}
							</option>
						</c:forEach>
					</select>
				</div>
			
			
				<div class="period_of_time_selection">
					<h2>Period of time</h2>
					
					<div class="select_buttons">
						<div id="select_button_week" 
							class="select_button ${selectedIsMonthly == true ? 'disabled-selection' : '' }" 
							onclick="activateWeekSelection()">Weekly report</div>
						<div id="select_button_month" 
							class="select_button ${selectedIsMonthly == null || selectedIsMonthly == false ? 'disabled-selection' : '' }" 
							onclick="activateMonthSelection()">Monthly report</div>
					</div>
				</div>
				
				<div id="month_week_selection">
				
					<div id="week_selection" class="${selectedIsMonthly == true ? 'disabled-selection' : '' }">
						
						
						<div class="selection_content">
							<h2>Week</h2>
							<div class="dropdown">
								<select name="weekFrom" class="dropdown-select">
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
						
						<div class="selection_content">
						
							<div class="col-33">
								<h2>Month</h2>
								<div class="dropdown">
									<select name="month" class="dropdown-select">
										<option value="">Select...</option>
										<c:forEach items="${months}" var="month">
											<option value="${month.id}" ${month.id eq selectedMonth ? "selected='selected'" : "" }>
												${month.name}
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
						
							<div class="col-33">
								<h2>Week from</h2>
								<div class="dropdown">
									<select name="weekFrom" class="dropdown-select">
										<option value="">Select...</option>
										<c:forEach items="${weeks}" var="week">
											<option value="${week}" ${week eq selectedWeekFrom ? "selected='selected'" : "" }>
												${week}
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="col-33">
								<h2>Week to</h2>
								<div class="dropdown">
									<select name="weekTo" class="dropdown-select">
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
					
				</div>
			
	
				<h2>Right</h2>
				<div class="dropdown">
					<select name="right" class="dropdown-select">
						<c:forEach items="${rights}" var="right">
							<option value="${right.id}" ${right.id eq selectedRight ? "selected='selected'" : "" }>
								${right.name}
							</option>
						</c:forEach>
					</select>
				</div>
			
			
			
				<h2>DCP</h2>
				<div class="dropdown">
					<select name="source" class="dropdown-select">
						<option value="">Select...</option>
						<c:forEach items="${sources}" var="source">
							<option value="${source.id}" ${source.id eq selectedSource ? "selected='selected'" : "" }>
								${source.name}
							</option>
						</c:forEach>
					</select>
				</div>
	
            
				<div id="buscaBot">
					<input type="button" value="<%= ReportController.EXPORT_ACTION %>" onclick="validateForm(this)" />
				</div>
				
			</div>
			
		</div>
		
		<br/>
		<jsp:include page="/WEB-INF/jsp/report/weekly_report_list.jsp"/>
		
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
