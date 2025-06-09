<%@page import="edu.ucam.util.Constantes"%>
<%@page import="edu.ucam.domain.Hueco"%>
<%@page import="java.util.List"%>
<%@page import="edu.ucam.domain.Espacio"%>
<%@page import="edu.ucam.domain.Usuario"%>
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="dad2" uri="http://ucam.edu/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel de administrador</title>
</head>
<body>
	<h1>Bienvenido al panel de administrador</h1>
	<h2>
		<a
			href="${pageContext.request.contextPath}/secured/admin/usermanagement.jsp">User Management</a>
	</h2>

	<h2>Lista de espacios</h2>
	<dad2:listaEspacios espacios="${applicationScope.LISTA_ESPACIOS}" />

	<h2>Añadir espacio</h2>
	<form action="<%= request.getContextPath() %>/control" method="POST">
		<input type="hidden" name="accion" value="crearespacio">
		Nombre: <input type="text" name="<%=Constantes.PARAM_NOMBRE%>" required><br> 
		<input type="submit" value="Crear espacio">
	</form>

	<br>

	<h2>Lista huecos</h2>

	<form action="<%=request.getContextPath()%>/control" method="POST">
		<input type="hidden" name="accion" value="verhuecos">
		<label for="espacio">Selecciona un espacio:</label> <select
			name="<%=Constantes.PARAM_ESPACIO%>" id="espacio">
			<%
			String espacioSeleccionado = request.getParameter(Constantes.PARAM_ESPACIO);
			HashSet<Espacio> espacios = (HashSet<Espacio>) application.getAttribute("LISTA_ESPACIOS");
			for (Espacio e : espacios) {
				String nombre = e.getNombre();
				boolean seleccionado = nombre.equalsIgnoreCase(espacioSeleccionado);
			%>
			<option value="<%=nombre%>" <%=seleccionado ? "selected" : ""%>><%=nombre%></option>
			<%
			}
			%>
		</select> <input type="submit" value="Buscar">
	</form>

	<%
	List<Hueco> huecos = (List<Hueco>) request.getAttribute("LISTA_HUECOS");
	if (huecos != null && espacioSeleccionado != null && !espacioSeleccionado.isEmpty()) {
	%>
	<h3>
		Huecos del espacio:
		<%=espacioSeleccionado%></h3>
	<ul>
		<%
		for (Hueco h : huecos) {
		%>
		<li><%=h.toString()%>
			<form action="<%=request.getContextPath()%>/control" method="post">
				<input type="hidden" name="accion" value="eliminarhueco">
				<input type="hidden" name="espacio" value="<%=espacioSeleccionado%>">
				<input type="hidden" name="fecha" value="<%=h.getFecha().toString()%>">
				<input type="submit" value="Eliminar">
			</form>
		</li>
		<%
		}
		%>
	</ul>
	<%
	}
	%>

	<br>

	<h2>Añadir hueco</h2>
	<form action="<%= request.getContextPath() %>/control" method="POST">
		<input type="hidden" name="accion" value="addhueco">
		<label for="espacio">Selecciona un espacio:</label> <select
			name="<%=Constantes.PARAM_ESPACIO%>" required>
			<%
			HashSet<Espacio> espaciosAdd = (HashSet<Espacio>) application.getAttribute("LISTA_ESPACIOS");
			for (Espacio e : espaciosAdd) {
			%>
			<option value="<%=e.getNombre()%>"><%=e.getNombre()%></option>
			<%
			}
			%>
		</select> <br> <label for="fecha">Fecha y hora del hueco:</label> 
		<input type="datetime-local" step="3600" name="<%=Constantes.PARAM_FECHA%>" required>
		<br> <input type="submit" value="Añadir hueco">
	</form>

	<br>

	<a href="${pageContext.request.contextPath}/secured/main.jsp">Volver al main</a>
</body>
</html>