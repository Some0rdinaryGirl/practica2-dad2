<%@page import="edu.ucam.util.Constantes"%>
<%@page import="edu.ucam.domain.Usuario"%>
<%@page import="edu.ucam.domain.Hueco"%>
<%@page import="java.util.List"%>
<%@page import="edu.ucam.domain.Espacio"%>
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Main page</title>
</head>
<body>
	<h1>Bienvenido</h1>
	<h2>Lista Huecos</h2>

	<form action="<%= request.getContextPath() %>/control" method="POST">
		<input type="hidden" name="accion" value="verhuecosuser">
		<label for="espacio">Selecciona un aula: </label>
		<select name="<%= Constantes.PARAM_ESPACIO %>" id="espacio">
			<% 	HashSet<Espacio> espacios = (HashSet<Espacio>) application.getAttribute("LISTA_ESPACIOS");
    	String espacioSeleccionado = request.getParameter(Constantes.PARAM_ESPACIO);
    	for (Espacio e : espacios) {
    		String nombre = e.getNombre();
    	     boolean seleccionado = nombre.equalsIgnoreCase(espacioSeleccionado);
    %>
			<option value="<%= nombre %>" <%= seleccionado ? "selected" : "" %>><%= nombre %></option>
			<% } %>
		</select> <input type="submit" value="Buscar horas libres">
	</form>

	<%
  		List<Hueco> huecos = (List<Hueco>) request.getAttribute("LISTA_HUECOS");
  		if (huecos != null) {
	%>
	<h3>
		Huecos del aula
		<%= espacioSeleccionado %>:
	</h3>
	<ul>
		<% for (Hueco h : huecos) { %>
		<li><%= h.toString() %> <% if (!h.isOcupado()) { %>
			<form action="<%= request.getContextPath() %>/control" method="post" style="display: inline;">
				<input type="hidden" name="accion" value="reservarhueco">
				<input type="hidden" name="espacio" value="<%= espacioSeleccionado %>">
				<input type="hidden" name="fecha" value="<%= h.getFecha().toString() %>">
				<input type="submit" value="Reservar">
			</form> <% } else { %> <span style="color: gray;"> (No disponible)</span> <% } %>
		</li>
		<% } %>
	</ul>
	<% } %>

	<br>

	<h2>Mis reservas</h2>
	<%
  Usuario usuarioSesion = (Usuario) session.getAttribute("USUARIO_SESION");
  List<Hueco> misReservas = usuarioSesion.getListaHuecosReservados();

  if (misReservas != null && !misReservas.isEmpty()) {
%>
	<ul>
		<% for (Hueco h : misReservas) { %>
		<li><%= h.toString() %>
			<form action="<%= request.getContextPath() %>/control" method="post" style="display: inline;">
				<input type="hidden" name="accion" value="cancelarreserva">
				<input type="hidden" name="fecha" value="<%= h.getFecha().toString() %>">
				<input type="submit" value="Cancelar reserva">
			</form>
		</li>
		<% } %>
	</ul>
	<%
  } else {
%>
	<p>No tienes reservas actualmente.</p>
	<%
  }
%>

<%
  if (usuarioSesion != null && usuarioSesion.isAdministrador()) {
%>
  <a href="<%= request.getContextPath() %>/secured/admin/panel.jsp"
     style="color: blue;">¡Eres administrador! Puedes acceder al panel
     de control.</a>
<%
  }
%>

</body>
</html>