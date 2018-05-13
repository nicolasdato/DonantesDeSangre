package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

public class BusquedaEsFavorito extends BusquedaCondicion {

	public BusquedaEsFavorito(Busqueda busqueda) {
		super(busqueda);
	}

	@Override
	protected Boolean condicion(Persona persona) {
		return persona.isFavorito();
	}
}
