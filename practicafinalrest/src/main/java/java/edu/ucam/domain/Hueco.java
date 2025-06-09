package edu.ucam.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hueco implements Serializable {
	private boolean ocupado;
	private LocalDateTime fecha;
	
	public Hueco(LocalDateTime fecha) {
		this.ocupado = false;
		this.fecha = fecha;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formato) + (ocupado ? " (Ocupado)" : " (Libre)");
    }
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Hueco h = (Hueco) obj;
	    return fecha.equals(h.fecha);
	}

	@Override
	public int hashCode() {
	    return fecha.hashCode();
	}
}
