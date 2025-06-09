package edu.ucam.listeners;

import java.io.File;
import java.util.HashSet;

import edu.ucam.dao.UsuarioDAO;
import edu.ucam.dao.implementacion.SerializableUsuarioDAO;
import edu.ucam.domain.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class UserListLoaderAndSaver implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		System.out.println("[INFO] Cargando usuarios...");

		// Crear carpeta si no existe
		String folderPath = context.getRealPath("WEB-INF/files");
		File folder = new File(folderPath);
		if (!folder.exists()) {
			System.out.println("[INFO] Carpeta /files no existía. Creando...");
			folder.mkdirs();
		}

		// Ruta completa al archivo
		String rutaUsuarios = folderPath + File.separator + "usuarios.ser";
		System.out.println("[INFO] Ruta usuarios.ser: " + rutaUsuarios);

		UsuarioDAO usuarioDAO = new SerializableUsuarioDAO(rutaUsuarios);
		HashSet<Usuario> listaUsuarios = usuarioDAO.obtenerTodos();

		if (listaUsuarios.isEmpty()) {
			Usuario admin = new Usuario("admin", "admin", true);
			usuarioDAO.insertar(admin);
			listaUsuarios = usuarioDAO.obtenerTodos();
			System.out.println("[INFO] Usuario admin creado por defecto.");
		}

		context.setAttribute("LISTA_USUARIOS", listaUsuarios);
		context.setAttribute("USUARIODAO", usuarioDAO);
		System.out.println("[INFO] Usuarios cargados con éxito.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("[INFO] Aplicación detenida. Guardando usuarios...");
		ServletContext context = sce.getServletContext();
		UsuarioDAO usuarioDAO = (UsuarioDAO) context.getAttribute("USUARIODAO");
		usuarioDAO.guardar((HashSet<Usuario>) context.getAttribute("LISTA_USUARIOS"));
	}
}