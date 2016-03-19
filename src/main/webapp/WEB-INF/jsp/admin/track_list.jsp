<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.bmat.digitalcharts.admin.controllers.Utils"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="track" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/song_list.js" />' ></script>
<script type="text/javascript" src='<c:url value="/js/admin/song_admin.js" />' ></script>

<input type="hidden" id="from" value="<%= Utils.TRACK_LIST %>"/>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

<div id="adminInt" class="tracks">

	<h1>TRACKS</h1>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Nombre canción</th>
					<th>Artista</th>
					<th>Id canción</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />