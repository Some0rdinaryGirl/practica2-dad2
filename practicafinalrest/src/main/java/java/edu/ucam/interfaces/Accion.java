package edu.ucam.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Accion {
	String ejecutar(HttpServletRequest request, HttpServletResponse response);
}
