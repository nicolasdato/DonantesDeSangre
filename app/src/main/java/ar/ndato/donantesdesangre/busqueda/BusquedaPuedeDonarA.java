package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.sangre.Sangre;
import ar.ndato.donantesdesangre.visitor.VisitorDonaA;

public class BusquedaPuedeDonarA extends BusquedaCondicion {

	private Sangre sangre;

	public BusquedaPuedeDonarA(Sangre sangre, Busqueda busqueda) {
		super(busqueda);
		this.sangre = sangre;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		VisitorDonaA visitor = persona.getSangre().getVisitorDonaA();
		sangre.accept(visitor);
		return visitor.puedeDonar();
	}
}
