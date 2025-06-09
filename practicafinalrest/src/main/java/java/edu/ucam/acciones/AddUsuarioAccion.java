package edu.ucam.acciones;

import java.util.HashSet;

import edu.ucam.util.Constantes;
import edu.ucam.dao.UsuarioDAO;
import edu.ucam.domain.Usuario;
import edu.ucam.interfaces.Accion;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddUsuarioAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		
		String nombre = request.getParameter(Constantes.PARAM_NOMBRE);
		String contrasena = request.getParameter(Constantes.PARAM_CONTRASENA);
		boolean esAdmin = request.getParameter("PARAM_ADMINISTRADOR") != null;
		
		Usuario usuarioAdd = new Usuario(nombre, contrasena, esAdmin);
		
		UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");
		usuarioDAO.insertar(usuarioAdd);
		
		HashSet<Usuario> listaUsuarios = (HashSet<Usuario>) context.getAttribute("LISTA_USUARIOS");
		listaUsuarios = usuarioDAO.obtenerTodos();
		context.setAttribute("LISTA_USUARIOS", listaUsuarios);
		
		return "/secured/admin/usermanagement.jsp";
	}

}
