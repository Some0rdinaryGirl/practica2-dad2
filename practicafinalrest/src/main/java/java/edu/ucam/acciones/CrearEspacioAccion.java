package edu.ucam.acciones;

import java.util.HashSet;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CrearEspacioAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		String nombreEspacio = request.getParameter(Constantes.PARAM_NOMBRE);
		
		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
		Espacio espacio = new Espacio(nombreEspacio);
		espacioDAO.insertar(espacio);
		
		HashSet<Espacio> listaEspacios = (HashSet<Espacio>) context.getAttribute("LISTA_ESPACIOS");
		listaEspacios = espacioDAO.obtenerTodos();
		context.setAttribute("LISTA_ESPACIOS", listaEspacios);
		
		return "/secured/admin/panel.jsp";
	}

}
