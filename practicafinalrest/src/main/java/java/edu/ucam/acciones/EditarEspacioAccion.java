package edu.ucam.acciones;

import java.util.HashSet;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarEspacioAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();

		String nombreAntiguo = request.getParameter("nombreAntiguo");
		String nombreNuevo = request.getParameter("nombreNuevo");

		if (nombreAntiguo == null || nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
			request.setAttribute("error", "El nuevo nombre no puede estar vacío.");
			return "/secured/admin/editEspacio.jsp?nombre=" + nombreAntiguo;
		}

		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
		HashSet<Espacio> espacios = espacioDAO.obtenerTodos();

		for (Espacio e : espacios) {
			if (e.getNombre().equalsIgnoreCase(nombreAntiguo)) {
				e.setNombre(nombreNuevo.trim());
				break;
			}
		}

		espacioDAO.guardar(espacios);
		context.setAttribute("LISTA_ESPACIOS", espacioDAO.obtenerTodos());

		return "/secured/admin/panel.jsp"; // Asegúrate de que este sea tu panel correcto
	}
}
