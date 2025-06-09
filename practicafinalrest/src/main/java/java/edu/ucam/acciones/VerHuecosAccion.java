package edu.ucam.acciones;

import java.util.HashSet;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VerHuecosAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
	    String nombreEspacio = request.getParameter(Constantes.PARAM_ESPACIO);

	    EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
	    HashSet<Espacio> espacios = espacioDAO.obtenerTodos();

	    for (Espacio e : espacios) {
	        if (e.getNombre().equalsIgnoreCase(nombreEspacio)) {
	            request.setAttribute("LISTA_HUECOS", e.getHuecos());
	            break;
	        }
	    }
	    
	    return "/secured/admin/panel.jsp";
	}

}
