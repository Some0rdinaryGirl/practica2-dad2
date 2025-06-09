package edu.ucam.listeners;

import java.io.File;
import java.util.HashSet;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.dao.implementacion.SerializableEspacioDAO;
import edu.ucam.domain.Espacio;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class EspacioListLoaderAndSaver implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		System.out.println("[INFO] Cargando espacios...");

		// Crear carpeta si no existe
		String folderPath = context.getRealPath("WEB-INF/files");
		File folder = new File(folderPath);
		if (!folder.exists()) {
			System.out.println("[INFO] Carpeta /files no existía. Creando...");
			folder.mkdirs();
		}

		// Ruta completa al archivo
		String rutaEspacios = folderPath + File.separator + "espacios.ser";
		System.out.println("[INFO] Ruta espacios.ser: " + rutaEspacios);

		EspacioDAO espacioDAO = new SerializableEspacioDAO(rutaEspacios);
		HashSet<Espacio> listaEspacios = espacioDAO.obtenerTodos();

		context.setAttribute("LISTA_ESPACIOS", listaEspacios);
		context.setAttribute("ESPACIODAO", espacioDAO);
		System.out.println("[INFO] Espacios cargados con éxito.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("[INFO] Aplicación detenida. Guardando espacios...");
		ServletContext context = sce.getServletContext();
		EspacioDAO espacioDAO = (EspacioDAO) context.getAttribute("ESPACIODAO");
		espacioDAO.guardar((HashSet<Espacio>) context.getAttribute("LISTA_ESPACIOS"));
	}
}