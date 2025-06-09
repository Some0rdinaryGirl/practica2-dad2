package edu.ucam.acciones;

import java.time.LocalDateTime;
import java.util.HashSet;

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

public class ReservarHuecoAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();

        String nombreEspacio = request.getParameter("espacio");
        String fechaTexto = request.getParameter("fecha");

        LocalDateTime fecha = LocalDateTime.parse(fechaTexto);

        HashSet<Espacio> listaEspacios = (HashSet<Espacio>) context.getAttribute("LISTA_ESPACIOS");
        Usuario usuario = (Usuario) session.getAttribute("USUARIO_SESION");

        for (Espacio espacio : listaEspacios) {
            if (espacio.getNombre().equalsIgnoreCase(nombreEspacio)) {
                espacio.reservarHueco(fecha);

                // Encontrar el hueco reservado y añadirlo al usuario
                for (Hueco h : espacio.getHuecos()) {
                    if (h.getFecha().equals(fecha)) {
                        usuario.addHueco(h);
                        break;
                    }
                }
                break;
            }
        }

        // Guardar los cambios
        UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");
        usuarioDAO.insertar(usuario);
        context.setAttribute("LISTA_USUARIOS", usuarioDAO.obtenerTodos());

        EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
        espacioDAO.guardar(listaEspacios);
        context.setAttribute("LISTA_ESPACIOS", listaEspacios);
        
        return "/secured/main.jsp";
	}

}
