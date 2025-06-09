package edu.ucam.dao;

import java.util.HashSet;

import edu.ucam.domain.Espacio;

public interface EspacioDAO {
	void insertar(Espacio e);
	HashSet<Espacio> obtenerTodos();
	void eliminar(String nombre);
	void guardar(HashSet<Espacio> listaEspacios);
}
