<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="track" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/song_list.js" />' ></script>
<script type="text/javascript" src='<c:url value="/js/admin/track_merge.js" />' ></script>

<input type="hidden" id="from" value="track_edit"/>
<input type="hidden" id="current_song_name" value="${track.name}"/>
<input type="hidden" id="current_song_id" value="${track.song.id}"/>

<div id="adminInt" class="editar-track">

	<h1>MODIFICAR TRACK</h1>
	
	<div class="campo-no-editable">
		Track ID: ${track.id}
	</div>
	<div class="campo-no-editable">
		NOMBRE: ${track.name}
	</div>
	<div class="campo-no-editable">
		ISRC: ${track.isrc}
	</div>
	
	<h3>Seleccione la canción a la cual quiere vincular este track:</h3>
	

	<form:form commandName="track" action="accept" method="POST">
	
		<form:hidden path="id"/>
		<form:hidden path="name"/>
		<form:hidden path="isrc"/>
		<form:hidden path="performer.id"/>
		<form:hidden path="release.id"/>
	
		
		<div class="Grid">

			<table class="datatable">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Artista</th>
						<th>Compositor</th>
						<th>Seleccionar</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			
		</div>
		
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />