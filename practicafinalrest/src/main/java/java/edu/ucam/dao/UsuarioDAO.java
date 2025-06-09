package edu.ucam.dao;

import java.util.HashSet;

import edu.ucam.domain.Espacio;
import edu.ucam.domain.Usuario;

public interface UsuarioDAO {
	void insertar(Usuario u);
	HashSet<Usuario> obtenerTodos();
	void eliminar(String nombre);
	void guardar(HashSet<Usuario> listaUsuarios);
}
