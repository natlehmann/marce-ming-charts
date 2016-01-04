<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="song" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/song_edit.js" />' ></script>


<div id="adminInt">

	<h1>${song.id == null ? "CREAR " : "MODIFICAR " } CANCIÓN</h1>
	

	<form:form commandName="song" action="accept" method="POST">
	
		<form:hidden path="id"/>
	
		<div class="campo">
			<form:label path="name">Nombre</form:label>
			<form:errors path="name" cssClass="error"/>
			<form:input path="name" cssErrorClass="error" size="50"/>
		</div>
		
		<div class="campo">
			<form:label path="performer.id">Artista</form:label>
			<form:errors path="performer.id" cssClass="error"/>
			<form:hidden path="performer.id" id="performerId"/>
			<input type="text" id="performerAutocomplete" name="performerName" value="${performerName}"
				oninput="cleanPerformerSelection()" />
		</div>
		
		<div class="campo">
			<form:label path="composers">Compositor/es</form:label>
			<form:errors path="composers" cssClass="error"/>
			<form:input path="composers" cssErrorClass="error" size="50"/>
		</div>
		
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />