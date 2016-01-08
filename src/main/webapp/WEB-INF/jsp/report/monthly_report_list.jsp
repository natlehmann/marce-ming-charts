<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='<c:url value="/js/admin/monthly_report_list.js" />' ></script>

<div id="adminInt">

	<h1>REPORTES MENSUALES</h1>
	
	<div class="Grid">

		<table class="datatable datatable-monthly">
			<thead>
				<tr>
					<th>Id</th>
					<th>Año</th>
					<th>Mes</th>
					<th>Semana desde</th>
					<th>Semana hasta</th>
					<th>País</th>
					<th>Derecho</th>
					<th>DCP</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>

