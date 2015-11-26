<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src='<c:url value="/js/admin.js" />' ></script>

<div class="admin-menu">
	<ul>
		<li><a href='<c:url value="/admin/fechaDestacada/listar" />'>Fechas destacadas</a></li>
	</ul>
</div>



<div id="dialog-eliminar" style="display:none;" title="Confirmación">
	<form action="eliminar" method="post">
		
		<input type="hidden" name="id" id="dialog-eliminar-id" value="" />
		
		<p>
			<span id="dialog-eliminar-mensaje">
				¿Está seguro que desea eliminar este elemento?
			</span>
		</p>
		
		<div class="ui-dialog-buttonpane">
			<input type="submit" value="Aceptar" />
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
	</form>
</div>