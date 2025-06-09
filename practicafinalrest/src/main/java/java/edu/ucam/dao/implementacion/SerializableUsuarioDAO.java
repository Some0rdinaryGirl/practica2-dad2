package edu.ucam.dao.implementacion;

import java.io.*;
import java.util.HashSet;

import edu.ucam.dao.UsuarioDAO;
import edu.ucam.domain.Usuario;

public class SerializableUsuarioDAO implements UsuarioDAO {

	private final String archivo;

	public SerializableUsuarioDAO(String rutaArchivo) {
		this.archivo = rutaArchivo;
	}

	@Override
	public void insertar(Usuario u) {
		HashSet<Usuario> listaUsuarios = obtenerTodos();
	    
	    // Elimina cualquier usuario con el mismo nombre (ignora mayúsculas y espacios)
	    listaUsuarios.removeIf(usuario -> usuario.getNombre().trim().equalsIgnoreCase(u.getNombre().trim()));
	    
	    // Añade el actualizado
	    listaUsuarios.add(u);
	    
	    guardar(listaUsuarios);
	}

	@Override
	public HashSet<Usuario> obtenerTodos() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (HashSet<Usuario>) ois.readObject();
        } catch (Exception e) {
            return new HashSet<>();
        }
	}

	@Override
	public void eliminar(String nombre) {
		HashSet<Usuario> listaUsuarios = obtenerTodos();
        listaUsuarios.removeIf(u -> u.getNombre().trim().equalsIgnoreCase(nombre.trim()));
        guardar(listaUsuarios);
	}
	
	@Override
	public void guardar(HashSet<Usuario> listaUsuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(listaUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}