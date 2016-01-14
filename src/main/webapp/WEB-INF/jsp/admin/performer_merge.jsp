<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="performer" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/performer_list.js" />' ></script>

<div id="adminInt" class="editar-track">

	<h1>FUNDIR ARTISTAS</h1>
	
	<div class="campo-no-editable">
		Performer ID: ${performer.id}
	</div>
	<div class="campo-no-editable">
		NOMBRE: ${performer.name}
	</div>
	
	<h3>Seleccione el artista con el cual se fusionará el artista actual.</h3>
	<h5>ATENCIÓN: Una vez finalizada esta operación, el artista de id ${performer.id} será
	completamente reemplazado por el seleccionado.</h5>
	

	<form action='<c:url value="/admin/performer/accept_merge"/>' method="POST">
	
		<input type="hidden" id="from" name="from" value="performer_merge"/>
		<input type="hidden" id="current_performer_name" value="${performer.name}"/>
		<input type="hidden" id="current_performer_id" name="currentId" value="${performer.id}"/>
			
		
		<div class="Grid">

			<table class="datatable">
				<thead>
					<tr>
						<th>Id</th>
						<th>Nombre</th>
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