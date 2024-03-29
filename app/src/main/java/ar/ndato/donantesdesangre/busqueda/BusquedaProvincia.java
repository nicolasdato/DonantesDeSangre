package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

public class BusquedaProvincia extends BusquedaCondicion {

	private String provincia;

	public BusquedaProvincia(String provincia, Busqueda busqueda) {
		super(busqueda);
		this.provincia = provincia.toLowerCase();
	}

	@Override
	protected Boolean condicion(Persona persona) {
		return persona.getProvincia().toLowerCase().contains(provincia);
	}
}
