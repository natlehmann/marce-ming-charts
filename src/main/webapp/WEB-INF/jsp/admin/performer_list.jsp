<%@page import="com.bmat.digitalcharts.admin.controllers.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="performer" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/performer_list.js" />' ></script>

<input type="hidden" id="from" value="<%= Utils.PERFORMER_LIST %>"/>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

<div id="adminInt">

	<h1>ARTISTAS</h1>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />