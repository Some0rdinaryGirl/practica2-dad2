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

public class EliminarHuecoAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
	    
	    String nombreEspacio = request.getParameter("espacio");
	    String fechaTexto = request.getParameter("fecha");
	    LocalDateTime fecha = LocalDateTime.parse(fechaTexto);

	    HashSet<Espacio> espacios = (HashSet<Espacio>) context.getAttribute("LISTA_ESPACIOS");
	    HashSet<Usuario> usuarios = (HashSet<Usuario>) context.getAttribute("LISTA_USUARIOS");

	    EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
	    UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");

	    for (Espacio e : espacios) {
	        if (e.getNombre().equalsIgnoreCase(nombreEspacio)) {
	            List<Hueco> huecos = e.getHuecos();
	            huecos.removeIf(h -> h.getFecha().equals(fecha));
	            break;
	        }
	    }

	    for (Usuario u : usuarios) {
	        List<Hueco> reservados = u.getListaHuecosReservados();
	        reservados.removeIf(h -> h.getFecha().equals(fecha));
	        usuarioDAO.insertar(u);
	    }

	    espacioDAO.guardar(espacios);
	    context.setAttribute("LISTA_ESPACIOS", espacios);

	    context.setAttribute("LISTA_USUARIOS", usuarioDAO.obtenerTodos());
	    
	    return "/secured/admin/panel.jsp";
	}

}
