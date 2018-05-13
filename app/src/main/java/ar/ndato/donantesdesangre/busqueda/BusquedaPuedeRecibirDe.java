package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.sangre.Sangre;
import ar.ndato.donantesdesangre.visitor.VisitorRecibeDe;

public class BusquedaPuedeRecibirDe extends BusquedaCondicion {

	private Sangre sangre;

	public BusquedaPuedeRecibirDe(Sangre sangre, Busqueda busqueda) {
		super(busqueda);
		this.sangre = sangre;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		VisitorRecibeDe visitor = persona.getSangre().getVisitorRecibeDe();
		sangre.accept(visitor);
		return visitor.puedeRecibir();
	}
}
