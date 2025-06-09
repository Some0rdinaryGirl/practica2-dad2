package edu.ucam.acciones;

import java.util.HashSet;

import edu.ucam.domain.Usuario;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class IniciarSesionAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		HashSet<Usuario> listaUsuarios = (HashSet<Usuario>) context.getAttribute("LISTA_USUARIOS");
		
		String nombre = request.getParameter(Constantes.PARAM_NOMBRE);
		String contrasena = request.getParameter(Constantes.PARAM_CONTRASENA);
		
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getContrasena().equals(contrasena)) {
				session.setAttribute("USUARIO_SESION", usuario);
				request.setAttribute("bienvenida", "Bienvenido " + usuario.getNombre());
				return "/secured/main.jsp";
			}
		}
		
		request.setAttribute("error", "El usuario o contraseña introducidos son invalidos");
		
		return "/index.jsp";
	}
	
}
