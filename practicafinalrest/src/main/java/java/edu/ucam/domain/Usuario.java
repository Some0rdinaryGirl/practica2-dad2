package edu.ucam.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Serializable {
	private String nombre;
	private String contrasena;
	private boolean administrador;
	private List<Hueco> listaHuecosReservados;
	
	public Usuario(String nombre, String contrasena, boolean administrador) {
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.administrador = administrador;
		this.listaHuecosReservados = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public boolean isAdministrador() {
		return administrador;
	}
	
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	public List<Hueco> getListaHuecosReservados() {
		return listaHuecosReservados;
	}

	public void setListaHuecosReservados(List<Hueco> listaHuecosReservados) {
		this.listaHuecosReservados = listaHuecosReservados;
	}
	
	public void addHueco(Hueco h) {
		this.listaHuecosReservados.add(h);
	}
	
	public void removeHueco(Hueco h) {
		this.listaHuecosReservados.remove(h);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		Usuario u = (Usuario) obj;
		return u.getNombre().equalsIgnoreCase(this.getNombre());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getNombre());
	}
	
	@Override
	public String toString() {
		return "Nombre: " + getNombre() + 
				", ¿Administrador?: " + isAdministrador();
	}
}
