package edu.ucam.acciones;

import java.util.HashSet;
import java.util.List;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.dao.UsuarioDAO;
import edu.ucam.domain.Espacio;
import edu.ucam.domain.Hueco;
import edu.ucam.domain.Usuario;
import edu.ucam.interfaces.Accion;
import edu.ucam.util.Constantes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EliminarUsuarioAccion implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");
		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
		
		String nombre = (String) request.getParameter(Constantes.PARAM_NOMBRE);
		
		Usuario usuarioEncontrado = null;
		HashSet<Usuario> listaUsuarios = usuarioDAO.obtenerTodos();
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getNombre().equalsIgnoreCase(nombre)) {
				usuarioEncontrado = usuario;
			}
		}
		
		List<Hueco> reservados = usuarioEncontrado.getListaHuecosReservados();

		System.out.println("Usuario encontrado: " + usuarioEncontrado.getNombre());
		System.out.println("Huecos reservados: " + usuarioEncontrado.getListaHuecosReservados().size());
		
		HashSet<Espacio> listaEspacios = espacioDAO.obtenerTodos();
		if (!reservados.isEmpty()) {
			System.out.println("Hola 0");
			for (Espacio espacio : listaEspacios) {
				System.out.println("Hola 1");
				for (Hueco hueco : espacio.getHuecos()) {
					System.out.println("Hola 2");
					for (Hueco huecoUsuario : reservados) {
						System.out.println("Hola 3");
						if (hueco.equals(huecoUsuario)) {
							System.out.println("Hola 4");
							hueco.setOcupado(false);
						}
					}
				}
			}
		}
		
		
		usuarioDAO.eliminar(nombre);
		listaUsuarios = usuarioDAO.obtenerTodos();
        espacioDAO.guardar(listaEspacios);
        listaEspacios = espacioDAO.obtenerTodos();
        context.setAttribute("LISTA_ESPACIOS", listaEspacios);
		context.setAttribute("LISTA_USUARIOS", listaUsuarios);
		
		request.setAttribute("exito_eliminar", "Usuario eliminado con exito");
		
		return "/secured/admin/usermanagement.jsp";
	}

}
