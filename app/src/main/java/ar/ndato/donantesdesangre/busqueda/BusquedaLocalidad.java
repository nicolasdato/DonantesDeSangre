package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

public class BusquedaLocalidad extends BusquedaCondicion {

	private String localidad;

	public BusquedaLocalidad(String localidad, Busqueda busqueda) {
		super(busqueda);
		this.localidad = localidad;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		return persona.getLocalidad().contains(localidad);
	}
}
