<%@page import="edu.ucam.util.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
	<h1>Por favor inicie sesion</h1>
	
	<form action="<%= request.getContextPath() %>/control" method="POST">
		<input type="hidden" name="accion" value="iniciarsesion">
		Nombre: <input type="text" value="admin" name="<%= Constantes.PARAM_NOMBRE %>"><br>
		Contraseña: <input type="text" value="admin" name="<%= Constantes.PARAM_CONTRASENA %>"><br>
		<input type="submit" value="Iniciar sesion">
	</form>
	
	<c:if test="${not empty error}">
		<p style="color:red;">${error}</p>
	</c:if>
</body>
</html>