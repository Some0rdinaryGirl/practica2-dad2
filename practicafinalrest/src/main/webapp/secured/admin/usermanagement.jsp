<%@page import="edu.ucam.util.Constantes"%>
<%@page import="edu.ucam.domain.Usuario"%>
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Management</title>
</head>
<body>
	<h2>Lista de usuarios actuales</h2>
	
	<ul>
	<% HashSet<Usuario> listaUsuarios = (HashSet<Usuario>) application.getAttribute("LISTA_USUARIOS");
	
	
	for (Usuario usuario : listaUsuarios) {
	%>
		<li>
			<%= usuario.toString() %>
			<form action="<%= request.getContextPath() %>/control" method="post" style="display:inline;">
				<input type="hidden" name="accion" value="eliminarusuario">
				<input type="hidden" name="PARAM_NOMBRE" value="<%= usuario.getNombre() %>">
				<input type="submit" value="Eliminar">
			</form>
		</li>
	<%
	}
	%>
	</ul>
	<c:if test="${not empty exito_eliminar}">
		<p style="color:green;">${exito_eliminar}</p>
	</c:if>
	
	<br>
	
	<h2>Añadir nuevo usuario</h2>
	<form action="<%= request.getContextPath() %>/control" method="POST">
		<input type="hidden" name="accion" value="addusuario">
		Nombre: <input type="text" name="<%= Constantes.PARAM_NOMBRE %>" required><br>
		Contraseña: <input type="text" name="<%= Constantes.PARAM_CONTRASENA %>" required><br>
		¿Administrador?: <input type="checkbox" name="AddUsuario.PARAM_ADMINISTRADOR"><br>
		<input type="submit" value="Añadir usuario">
	</form>
	
	<br>
	
	<a href="${pageContext.request.contextPath}/secured/admin/panel.jsp" style="color: blue;">Volver al panel central</a>
</body>
</html>