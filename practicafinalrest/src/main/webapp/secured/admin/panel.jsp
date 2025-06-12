<%@page import="edu.ucam.util.Constantes"%>
<%@page import="edu.ucam.domain.Espacio"%>
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel de administrador</title>
<script>
    const contextPath = "<%= request.getContextPath() %>";

    async function obtenerHuecos() {
        const espacio = document.getElementById("espacioHuecos").value;
        const url = window.location.origin + contextPath + "/api/espacios/" + encodeURIComponent(espacio) + "/huecos";

        const contenedor = document.getElementById("listaHuecos");
        contenedor.innerHTML = "";

        try {
            const resp = await fetch(url);
            if (!resp.ok) {
                contenedor.innerHTML = "<p style='color:red;'>Error al obtener huecos</p>";
                return;
            }

            const huecos = await resp.json();
            if (huecos.length === 0) {
                contenedor.innerHTML = "<p>No hay huecos para este espacio.</p>";
                return;
            }

            const ul = document.createElement("ul");
            huecos.forEach(h => {
                const li = document.createElement("li");
                const date = new Date(h.fecha);
                const formatted = date.toLocaleString("es-ES", {
                    day: "2-digit", month: "2-digit", year: "numeric",
                    hour: "2-digit", minute: "2-digit"
                });

                li.textContent = formatted + (h.ocupado ? " (Ocupado)" : " (Libre)");

                const btn = document.createElement("button");
                btn.textContent = "Eliminar";
                btn.onclick = () => eliminarHueco(espacio, h.fecha);
                li.appendChild(document.createTextNode(" "));
                li.appendChild(btn);

                ul.appendChild(li);
            });

            contenedor.appendChild(ul);
        } catch (err) {
            console.error(err);
            contenedor.innerHTML = "<p style='color:red;'>Fallo en la conexión al servidor REST</p>";
        }
    }

    async function eliminarHueco(nombreEspacio, fechaIso) {
        if (!confirm("¿Eliminar hueco de " + nombreEspacio + " con fecha " + fechaIso + "?")) return;
        const url = window.location.origin + contextPath + "/api/espacios/" + encodeURIComponent(nombreEspacio) + "/huecos/" + encodeURIComponent(fechaIso);

        try {
            const resp = await fetch(url, { method: "DELETE" });
            if (resp.ok) {
                alert("Hueco eliminado.");
                obtenerHuecos();
            } else {
                alert("Error al eliminar hueco.");
            }
        } catch (err) {
            alert("Error de conexión con el servidor.");
            console.error(err);
        }
    }

    async function addHuecoREST(e) {
        e.preventDefault();
        const form = e.target;
        const espacio = form.espacio.value;
        const fechaLocal = form.fecha.value;
        const fechaISO = fechaLocal + ":00";  // ejemplo: "2025-06-12T13:00" → "2025-06-12T13:00:00"

        const url = window.location.origin + contextPath + "/api/espacios/" + encodeURIComponent(espacio) + "/huecos";

        try {
            const resp = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ fecha: fechaISO, ocupado: false })
            });

            if (resp.ok) {
                alert("Hueco añadido con éxito");
                obtenerHuecos();
            } else {
                alert("Error al añadir hueco");
            }
        } catch (err) {
            console.error(err);
            alert("Fallo en la conexión al servidor");
        }
    }
</script>
</head>
<body>
	<h1>Bienvenido al panel de administrador</h1>

	<h2>
		<a href="${pageContext.request.contextPath}/secured/admin/usermanagement.jsp">
			User Management
		</a>
	</h2>

	<h2>Lista de espacios</h2>
	<ul>
	<%
		String endpoint = "http://localhost:8080/practicafinalrest/api/espacios";
		HashSet<Espacio> espacios = edu.ucam.util.EspacioRestClient.obtenerEspacios(endpoint);
		for (Espacio e : espacios) {
			String nombre = e.getNombre();
	%>
		<li>
			<span id="espacio-<%= nombre %>"><%= nombre %></span>

			<!-- Botón eliminar con JS -->
			<button onclick="eliminarEspacio('<%= nombre %>')">Eliminar</button>

			<!-- Botón editar normal -->
			<form action="<%= request.getContextPath() %>/secured/admin/editEspacio.jsp" method="GET" style="display:inline;">
				<input type="hidden" name="<%= Constantes.PARAM_NOMBRE %>" value="<%= nombre %>">
				<input type="submit" value="Editar">
			</form>
		</li>
	<% } %>
	</ul>

	<script>
		function eliminarEspacio(nombre) {
			if (confirm("¿Estás seguro de que deseas eliminar el espacio '" + nombre + "'?")) {
				fetch("http://localhost:8080/practicafinalrest/api/espacios/" + encodeURIComponent(nombre), {
					method: "DELETE"
				}).then(response => {
					if (response.ok) {
						alert("Espacio eliminado correctamente.");
						location.reload();
					} else {
						alert("Error al eliminar el espacio.");
					}
				}).catch(error => {
					console.error("Error:", error);
					alert("No se pudo conectar al servidor REST.");
				});
			}
		}
	</script>

	<h2>Lista huecos</h2>
	<label for="espacioHuecos">Selecciona un espacio:</label>
	<select id="espacioHuecos">
		<% for (Espacio e : espacios) { %>
			<option value="<%=e.getNombre()%>"><%=e.getNombre()%></option>
		<% } %>
	</select>
	<button onclick="obtenerHuecos()">Buscar huecos</button>

	<div id="listaHuecos"></div>

	<br>

	<h2>Añadir hueco</h2>
	<form onsubmit="addHuecoREST(event)">
		<label for="espacio">Selecciona un espacio:</label> 
		<select name="espacio" required>
			<% for (Espacio e : espacios) { %>
				<option value="<%=e.getNombre()%>"><%=e.getNombre()%></option>
			<% } %>
		</select>
		<br> 
		<label for="fecha">Fecha y hora del hueco:</label> 
		<input type="datetime-local" step="3600" name="fecha" required>
		<br> 
		<input type="submit" value="Añadir hueco">
	</form>

	<br>
	<a href="${pageContext.request.contextPath}/secured/main.jsp">Volver al main</a>
</body>
</html>
