<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>BMat Digital Charts ADMIN</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<script type="text/javascript" src='<c:url value="/js/jquery-1.10.2.min.js" />' ></script>	
		<script type="text/javascript" src='<c:url value="/js/jquery-ui-1.10.3.custom.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.dataTables.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.dataTables.filteronenter.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.ui.datepicker-es.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/master.js" />' ></script>
		
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/estadisticas/jquery-ui-1.10.3.custom.min.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery.dataTables.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery.dataTables_themeroller.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/master.css" />'>
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery-estadisticas.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/login.css" />'>	
		<link rel="stylesheet" type="text/css" media="screen and (max-width:1025px)" href='<c:url value="/css/master-mobile.css" />'>	
		
	</head>
	
	<%
		String itemMenuSeleccionado = request.getParameter("itemMenuSeleccionado") != null ?
				request.getParameter("itemMenuSeleccionado") : "";
	%>
	
	<body id="bmat-admin">
		
		<input type="hidden" id="contexto" value='<c:url value="/" />' />
		
		<div class="contenedor">  

        	<div class="top">
            	<div id="logo">
            		<a href='<c:url value="/home"/>'>
	            		<img src='<c:url value="/images/bmat.jpg"/>' />
            		</a>
            	</div>
            	
            	<sec:authorize access="isAuthenticated()">
		            <div id="logout">
		            	<strong>Usuario:</strong>  <sec:authentication property="principal.username" /> / 
		            	<a href='<c:url value="/logout"/>'>Salir</a>
		            </div>
		       </sec:authorize>
        	</div>
        	
        	
			<div class="menu">
        
           		<div id="button-menu"> 
           		<div class="item-menu">
           			<a href='<c:url value="/admin/performer/list"/>' 
           				class='<%= itemMenuSeleccionado.equals("performer") ? "activo" : "" %>'>
           				ARTISTAS
           			</a>
           		</div> 
           		
           		<div class="item-menu">
           			<a href='<c:url value="/admin/song/list"/>' 
           				class='<%= itemMenuSeleccionado.equals("song") ? "activo" : "" %>'>
           				CANCIONES
           			</a>
           		</div>
           		
           		<div class="item-menu">
           			<a href='<c:url value="/admin/track/list"/>' 
           				class='<%= itemMenuSeleccionado.equals("track") ? "activo" : "" %>'>
           				TRACKS
           			</a>
           		</div>
           		
           		<div class="item-menu">
           			<a href='<c:url value="/report/filters"/>' 
           				class='<%= itemMenuSeleccionado.equals("report") ? "activo" : "" %>'>
           				GENERAR REPORTE
           			</a>
           		</div>
           		
           		<div class="item-menu">
           			<a href='<c:url value="/admin/email/list"/>' 
           				class='<%= itemMenuSeleccionado.equals("email") ? "activo" : "" %>'>
           				EMAILS
           			</a>
           		</div>
           		
           		</div>
            		
			</div>
	