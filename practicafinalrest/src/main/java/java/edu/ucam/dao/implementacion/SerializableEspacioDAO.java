package edu.ucam.dao.implementacion;

import java.io.*;
import java.util.HashSet;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.domain.Espacio;

public class SerializableEspacioDAO implements EspacioDAO {

	private final String archivo;

	public SerializableEspacioDAO(String rutaArchivo) {
		this.archivo = rutaArchivo;
	}

	@Override
	public void insertar(Espacio e) {
		HashSet<Espacio> listaEspacios = obtenerTodos();
		listaEspacios.add(e);
		guardar(listaEspacios);
	}

	@Override
	public HashSet<Espacio> obtenerTodos() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (HashSet<Espacio>) ois.readObject();
        } catch (Exception e) {
            return new HashSet<>();
        }
	}

	@Override
	public void eliminar(String nombre) {
		HashSet<Espacio> listaEspacios = obtenerTodos();
		listaEspacios.removeIf(e -> e.getNombre().trim().equalsIgnoreCase(nombre.trim()));
        guardar(listaEspacios);
	}
	
	@Override
	public void guardar(HashSet<Espacio> listaEspacios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(listaEspacios);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
