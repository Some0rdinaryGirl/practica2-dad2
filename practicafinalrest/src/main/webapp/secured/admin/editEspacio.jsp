<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String nombreAntiguo = request.getParameter("nombre");
    if (nombreAntiguo == null || nombreAntiguo.isEmpty()) {
%>
    <p>Error: no se ha recibido el nombre del espacio a editar.</p>
    <a href="<%= request.getContextPath() %>/secured/admin/paneladmin.jsp">Volver</a>
<%
    return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar espacio</title>
</head>
<body>
    <h1>Editar espacio</h1>

    <form action="<%= request.getContextPath() %>/control" method="POST">
        <input type="hidden" name="accion" value="editarespacio">
        <input type="hidden" name="nombreAntiguo" value="<%= nombreAntiguo %>">

        <label for="nombreNuevo">Nuevo nombre del espacio:</label><br>
        <input type="text" id="nombreNuevo" name="nombreNuevo" required value="<%= nombreAntiguo %>"><br><br>

        <input type="submit" value="Guardar cambios">
    </form>

    <br>
    <a href="<%= request.getContextPath() %>/secured/admin/paneladmin.jsp">Cancelar</a>
</body>
</html>
