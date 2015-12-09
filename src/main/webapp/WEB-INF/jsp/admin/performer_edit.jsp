<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="performer" name="itemMenuSeleccionado"/>
</jsp:include>


<div id="adminInt">

	<h1>${performer.id == null ? "CREAR " : "MODIFICAR " } ARTISTA</h1>
	

	<form:form commandName="performer" action="accept" method="POST">
	
		<form:hidden path="id"/>
	
		<div class="campo">
			<form:label path="name">Nombre</form:label>
			<form:errors path="name" cssClass="error"/>
			<form:input path="name" cssErrorClass="error"/>
		</div>
		
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />