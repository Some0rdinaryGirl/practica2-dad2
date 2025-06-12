<%@page import="edu.ucam.util.Constantes"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String nombreAntiguo = request.getParameter(Constantes.PARAM_NOMBRE);
    if (nombreAntiguo == null || nombreAntiguo.isEmpty()) {
%>
    <p>Error: no se ha recibido el nombre del espacio a editar.</p>
    <a href="<%= request.getContextPath() %>/secured/admin/panel.jsp">Volver</a>
<%
    return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar espacio</title>
    <script>
        async function editarEspacio(event) {
            event.preventDefault();

            const nombreAntiguo = "<%= nombreAntiguo %>";
            const nombreNuevo = document.getElementById("nombreNuevo").value;

            const response = await fetch("http://localhost:8080/practicafinalrest/api/espacios/" + encodeURIComponent(nombreAntiguo), {

                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nombre: nombreNuevo })
            });

            if (response.ok) {
                alert("Espacio editado correctamente.");
                window.location.href = "<%= request.getContextPath() %>/secured/admin/panel.jsp";
            } else {
                alert("Error al editar el espacio.");
            }
        }
    </script>
</head>
<body>
    <h1>Editar espacio</h1>

    <form onsubmit="editarEspacio(event)">
        <label for="nombreNuevo">Nuevo nombre del espacio:</label><br>
        <input type="text" id="nombreNuevo" name="nombreNuevo" required value="<%= nombreAntiguo %>"><br><br>
        <input type="submit" value="Guardar cambios">
    </form>

    <br>
    <a href="<%= request.getContextPath() %>/secured/admin/panel.jsp">Cancelar</a>
</body>
</html>
