<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="song" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/song_list.js" />' ></script>
<script type="text/javascript" src='<c:url value="/js/admin/song_admin.js" />' ></script>

<div id="adminInt" class="editar-track">

	<h1>FUNDIR CANCIONES</h1>
	
	<div class="campo-no-editable">
		Canción ID: ${song.id}
	</div>
	<div class="campo-no-editable">
		NOMBRE: ${song.name}
	</div>
	<div class="campo-no-editable">
		COMPOSITORES: ${song.composers}
	</div>
	
	<h3>Seleccione la canción con la cual se fusionará la canción actual.</h3>
	<h5>ATENCIÓN: Una vez finalizada esta operación, la canción de id ${song.id} será
	completamente reemplazada por la seleccionada.</h5>
	

	<form action='<c:url value="/admin/song/accept_merge"/>' method="POST">
	
		<input type="hidden" id="from" name="from" value="song_merge"/>
		<input type="hidden" id="current_song_name" value="${song.name}"/>
		<input type="hidden" name="currentId" value="${song.id}"/>
			
		
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
			<input type="submit" name="Aceptar" value="Aceptar"/>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />