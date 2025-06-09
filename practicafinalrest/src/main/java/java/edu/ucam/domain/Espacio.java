package edu.ucam.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Espacio implements Serializable {
	private String nombre;
	private List<Hueco> huecos;
	
	public Espacio(String nombre) {
		this.nombre = nombre;
		this.huecos = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setHuecos(List<Hueco> listaHuecos) {
		this.huecos = listaHuecos;
	}
	
	public void addHueco(Hueco h) {
        huecos.add(h);
    }

    public List<Hueco> getHuecos() {
        return huecos;
    }

    public void reservarHueco(LocalDateTime fecha) {
        for (Hueco h : huecos) {
            if (h.getFecha().equals(fecha)) {
                h.setOcupado(true);
            }
        }
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		Espacio e = (Espacio) obj;
		return e.getNombre().equalsIgnoreCase(getNombre());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getNombre());
	}
	
	@Override
	public String toString() {
		return "Nombre: " + getNombre();
	}
}
