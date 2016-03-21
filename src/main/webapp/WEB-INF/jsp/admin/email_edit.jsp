<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="email" name="itemMenuSeleccionado"/>
</jsp:include>


<div id="adminInt" class="email">

	<h1>${emailAddress.id == null ? "CREAR " : "MODIFICAR " } DIRECCIÓN DE EMAIL</h1>
	

	<form:form commandName="emailAddress" action="accept" method="POST">
	
		<form:hidden path="id"/>
	
		<div class="campo">
			<form:label path="email">Email</form:label>
			<form:errors path="email" cssClass="error"/>
			<form:input path="email" cssErrorClass="error"/>
		</div>
		
		
		<div class="campo">
			<form:label path="restSource.id">DCP</form:label>
			
			<div class="dropdown">
				<select name="restSource.id" class="dropdown-select">
					<c:forEach items="${sources}" var="source">
						<option value="${source.id}" ${source.id eq selectedSource ? "selected='selected'" : "" }>
							${source.name}
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />