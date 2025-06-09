package edu.ucam.acciones;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EliminarEspacioAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		String nombreEliminar = request.getParameter(Constantes.PARAM_NOMBRE);

		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");

		espacioDAO.eliminar(nombreEliminar);

		context.setAttribute("LISTA_ESPACIOS", espacioDAO.obtenerTodos());

		return "/secured/admin/panel.jsp";
	}

}
