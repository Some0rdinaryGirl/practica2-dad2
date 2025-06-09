package edu.ucam.acciones;

import java.time.LocalDateTime;
import java.util.HashSet;

import edu.ucam.util.Constantes;
import edu.ucam.dao.EspacioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.domain.Hueco;
import edu.ucam.interfaces.Accion;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddHuecoAccion implements Accion {
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		
		String nombreEspacio = request.getParameter(Constantes.PARAM_ESPACIO);
		String fechaTexto = request.getParameter(Constantes.PARAM_FECHA);
		LocalDateTime fecha = LocalDateTime.parse(fechaTexto);
		
		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
		HashSet<Espacio> listaEspacios = espacioDAO.obtenerTodos();
		
		for (Espacio espacio : listaEspacios) {
	        if (espacio.getNombre().equalsIgnoreCase(nombreEspacio)) {
	            espacio.addHueco(new Hueco(fecha));
	            break;
	        }
	    }
		
		// Guardar la lista actualizada
	    espacioDAO.guardar(listaEspacios);
	    context.setAttribute("LISTA_ESPACIOS", listaEspacios);

	    request.setAttribute("exito", "Hueco añadido correctamente.");

		return "/secured/admin/panel.jsp";
	}

}
