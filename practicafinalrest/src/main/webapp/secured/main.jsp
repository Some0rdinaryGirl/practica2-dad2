<%@page import="edu.ucam.util.Constantes"%>
<%@page import="edu.ucam.domain.Usuario"%>
<%@page import="edu.ucam.domain.Hueco"%>
<%@page import="edu.ucam.domain.Espacio"%>
<%@page import="edu.ucam.util.EspacioRestClient"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Construimos la URL base para el servicio REST de espacios
    String scheme = request.getScheme();             // http
    String server = request.getServerName();         // localhost
    int port = request.getServerPort();              // 8080
    String context = request.getContextPath();       // /practicafinalrest
    String endpoint = scheme + "://" + server + ":" + port + context + "/api/espacios";

    // Obtenemos los espacios vía REST
    Set<Espacio> espacios = EspacioRestClient.obtenerEspacios(endpoint);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <script>
        // Ruta base de la API (sin dominio)
        const contextPath = "<%= request.getContextPath() %>";

        async function obtenerHuecos() {
            const espacio = document.getElementById("espacio").value;
            const url = window.location.origin
                      + contextPath
                      + "/api/espacios/"
                      + encodeURIComponent(espacio)
                      + "/huecos";

            const listaDiv = document.getElementById("listaHuecos");
            listaDiv.innerHTML = ""; // limpiamos

            try {
                const resp = await fetch(url, { method: "GET" });
                if (!resp.ok) {
                    listaDiv.innerHTML = "<p style='color:red;'>Error al obtener huecos</p>";
                    return;
                }
                const huecos = await resp.json();
                if (huecos.length === 0) {
                    listaDiv.innerHTML = "<p>No hay huecos disponibles para este espacio.</p>";
                    return;
                }

                // Título y lista
                document.getElementById("tituloHuecos").innerText =
                    "Huecos del aula " + espacio + ":";
                const ul = document.createElement("ul");

                huecos.forEach(h => {
                    const li = document.createElement("li");
                    const date = new Date(h.fecha);
                    const formatted = date.toLocaleString("es-ES", {
                        day: "2-digit", month: "2-digit", year: "numeric",
                        hour: "2-digit", minute: "2-digit"
                    });

                    li.innerHTML = formatted + (h.ocupado ? " (Ocupado)" : " (Libre)");

                    // Si está libre, añadimos botón de reserva
                    if (!h.ocupado) {
                        const form = document.createElement("form");
                        form.method = "post";
                        form.action = contextPath + "/control";
                        form.style.display = "inline";

                        // Input oculto: acción
                        const accionInput = document.createElement("input");
                        accionInput.type = "hidden";
                        accionInput.name = "accion";
                        accionInput.value = "reservarhueco";
                        form.appendChild(accionInput);

                        // Input oculto: fecha
                        const fechaInput = document.createElement("input");
                        fechaInput.type = "hidden";
                        fechaInput.name = "fecha";
                        fechaInput.value = h.fecha; // formato ISO
                        form.appendChild(fechaInput);

                        // Input oculto: espacio (por si es necesario en tu servlet)
                        const espacioInput = document.createElement("input");
                        espacioInput.type = "hidden";
                        espacioInput.name = "espacio";
                        espacioInput.value = espacio;
                        form.appendChild(espacioInput);

                        // Botón de enviar
                        const boton = document.createElement("input");
                        boton.type = "submit";
                        boton.value = "Reservar";
                        form.appendChild(boton);

                        li.appendChild(document.createTextNode(" "));
                        li.appendChild(form);
                    }

                    ul.appendChild(li);
                });


                listaDiv.appendChild(ul);
            } catch (err) {
                console.error(err);
                listaDiv.innerHTML = "<p style='color:red;'>Fallo en la conexión al servidor REST</p>";
            }
        }
    </script>
</head>
<body>
    <h1>Bienvenido</h1>

    <h2>Lista Huecos</h2>
    <label for="espacio">Selecciona un aula:</label>
    <select id="espacio" name="<%= Constantes.PARAM_ESPACIO %>">
        <% for (Espacio e : espacios) { %>
            <option value="<%= e.getNombre() %>"><%= e.getNombre() %></option>
        <% } %>
    </select>
    <button type="button" onclick="obtenerHuecos()">Buscar horas libres</button>

    <h3 id="tituloHuecos"></h3>
    <div id="listaHuecos"></div>

    <br>

    <h2>Mis reservas</h2>
    <%
        Usuario usuarioSesion = (Usuario) session.getAttribute("USUARIO_SESION");
        List<Hueco> misReservas = usuarioSesion.getListaHuecosReservados();
        if (misReservas != null && !misReservas.isEmpty()) {
    %>
    <ul>
        <% for (Hueco h : misReservas) { %>
        <li>
            <%= h.toString() %>
            <form action="<%= request.getContextPath() %>/control" method="post" style="display:inline;">
                <input type="hidden" name="accion" value="cancelarreserva"/>
                <input type="hidden" name="fecha" value="<%= h.getFecha().toString() %>"/>
                <input type="submit" value="Cancelar reserva"/>
            </form>
        </li>
        <% } %>
    </ul>
    <% } else { %>
        <p>No tienes reservas actualmente.</p>
    <% } %>

    <%
        if (usuarioSesion != null && usuarioSesion.isAdministrador()) {
    %>
    <a href="<%= request.getContextPath() %>/secured/admin/panel.jsp"
       style="color:blue;">¡Eres administrador! Puedes acceder al panel de control.</a>
    <% } %>
</body>
</html>
