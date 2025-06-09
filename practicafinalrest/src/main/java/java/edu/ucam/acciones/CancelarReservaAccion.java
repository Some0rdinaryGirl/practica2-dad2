package edu.ucam.acciones;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.dao.UsuarioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.domain.Hueco;
import edu.ucam.domain.Usuario;
import edu.ucam.interfaces.Accion;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CancelarReservaAccion implements Accion {

    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();

        // Obtener y validar el parámetro de fecha
        String fechaTexto = request.getParameter("fecha");
        if (fechaTexto == null || fechaTexto.isEmpty()) {
            request.setAttribute("error", "Fecha no proporcionada.");
            return "/secured/error.jsp";
        }

        LocalDateTime fecha;
        try {
            fecha = LocalDateTime.parse(fechaTexto);
        } catch (Exception e) {
            request.setAttribute("error", "Formato de fecha inválido.");
            return "/secured/error.jsp";
        }

        Usuario usuario = (Usuario) session.getAttribute("USUARIO_SESION");
        if (usuario == null) {
            return "/login.jsp"; // Redirige si no hay sesión activa
        }

        // Buscar el hueco en la lista de reservas del usuario y eliminarlo
        List<Hueco> reservados = usuario.getListaHuecosReservados();
        Hueco huecoAEliminar = null;

        for (Hueco h : reservados) {
            if (h.getFecha().equals(fecha)) {
                huecoAEliminar = h;
                break;
            }
        }

        if (huecoAEliminar != null) {
            reservados.remove(huecoAEliminar); // elimina el hueco reservado del usuario
        }

        // Buscar el hueco en la lista global y marcarlo como libre
        HashSet<Espacio> espacios = (HashSet<Espacio>) context.getAttribute("LISTA_ESPACIOS");
        for (Espacio e : espacios) {
            for (Hueco h : e.getHuecos()) {
                if (h.getFecha().equals(fecha)) {
                    h.setOcupado(false); // libera el hueco
                    break;
                }
            }
        }

        // Guardar los cambios en los DAOs
        UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");
        usuarioDAO.insertar(usuario); // guarda cambios del usuario
        context.setAttribute("LISTA_USUARIOS", usuarioDAO.obtenerTodos());

        EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
        espacioDAO.guardar(espacios);
        context.setAttribute("LISTA_ESPACIOS", espacios);

        // Actualizar el usuario en sesión (esto evita desconexiones)
        session.setAttribute("USUARIO_SESION", usuario);

        return "/secured/main.jsp"; // Redirige a la vista principal
    }
}