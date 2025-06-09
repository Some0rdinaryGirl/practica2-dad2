package edu.ucam.acciones;

import edu.ucam.interfaces.Accion;

public class FactoriaAcciones {
	public static Accion getAccion(String nombre) {
		switch (nombre) {
			case "addhueco": return new AddHuecoAccion();
			case "addusuario": return new AddUsuarioAccion();
			case "cancelarreserva": return new CancelarReservaAccion();
			case "crearespacio": return new CrearEspacioAccion();
			case "eliminarespacio": return new EliminarEspacioAccion();
			case "eliminarhueco": return new EliminarHuecoAccion();
			case "eliminarusuario": return new EliminarUsuarioAccion();
			case "iniciarsesion": return new IniciarSesionAccion();
			case "reservarhueco": return new ReservarHuecoAccion();
			case "verhuecos": return new VerHuecosAccion();
			case "verhuecosuser": return new VerHuecosUserAccion();
			case "editarespacio": return new EditarEspacioAccion();
			default: return new AccionPorDefecto();
		}
	}
}
