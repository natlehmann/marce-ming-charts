<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="track" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/track_list.js" />' ></script>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

<div id="adminInt" class="tracks">

	<h1>TRACKS</h1>
	
	<input type="hidden" id="songId" value="${song.id}"/>
	
	<div class="campo-no-editable">
		ID CANCIÓN: ${song.id}
	</div>
	<div class="campo-no-editable">
		NOMBRE CANCIÓN: ${song.name}
	</div>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Artista</th>
					<th>Compañía</th>
					<th>Album</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
	
	<div class="col-30">
		<div id="buscaBot">
			<a href='<c:url value="/admin/track/list"/>' class="classname">
				VOLVER
			</a>
		</div>
	</div>
</div>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />