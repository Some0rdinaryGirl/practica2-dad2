package edu.ucam.acciones;

import edu.ucam.interfaces.Accion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccionPorDefecto implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
        return "/index.jsp";
	}
	
}
