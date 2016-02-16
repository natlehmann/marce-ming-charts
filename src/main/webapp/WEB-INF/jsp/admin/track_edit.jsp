<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="track" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/admin/track_edit.js" />' ></script>

<div id="adminInt">

	<h1>MODIFICAR TRACK</h1>
	

	<form:form commandName="track" action="acceptEdition" method="POST">
	
		<form:hidden path="id"/>
		<form:hidden path="isrc"/>
		<form:hidden path="name"/>
		<form:hidden path="performer.id"/>
		<form:hidden path="song.id"/>
	
		<div class="campo">
			<form:label path="release.labelCompany.name">Album</form:label>
			<form:errors path="release.labelCompany.name" cssClass="error"/>
			<form:hidden path="release.labelCompany.id" id="labelCompanyId"/>
			<input type="text" id="labelCompanyAutocomplete" name="labelCompanyName" value="${labelCompanyName}"
				oninput="cleanLabelCompanySelection()" />
		</div>
		
		
		<div class="campo">
			<form:label path="release.licensor.name">Compañía</form:label>
			<form:errors path="release.licensor.name" cssClass="error"/>
			
			<form:select path="release.licensor.id">
				<form:options items="${licensors}" itemLabel="name" itemValue="id"/>
			</form:select>
		</div>
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('list')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />