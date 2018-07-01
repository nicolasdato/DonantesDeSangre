package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class BusquedaTieneSangre extends BusquedaCondicion {

	private Sangre sangre;

	public BusquedaTieneSangre(Sangre sangre, Busqueda busqueda) {
		super(busqueda);
		this.sangre = sangre;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		return persona.getSangre().equals(sangre);
	}
}
