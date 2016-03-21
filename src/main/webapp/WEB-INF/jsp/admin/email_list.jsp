<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="email" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/email_list.js" />' ></script>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

<div id="crear">
	<button type="button" onclick="ir('create')">Nueva dirección de email</button>
</div>

<div id="adminInt">

	<h1>EMAILS</h1>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Email</th>
					<th>DCP</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />