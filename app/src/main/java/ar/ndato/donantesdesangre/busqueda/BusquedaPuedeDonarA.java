package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.sangre.Sangre;
import ar.ndato.donantesdesangre.visitor.VisitorDonacion;

public class BusquedaPuedeDonarA extends BusquedaCondicion {

	private Sangre sangre;
	private Donacion.TipoDonacion tipoDonacion;

	public BusquedaPuedeDonarA(Sangre sangre, Donacion.TipoDonacion tipoDonacion, Busqueda busqueda) {
		super(busqueda);
		this.sangre = sangre;
		this.tipoDonacion = tipoDonacion;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		VisitorDonacion visitor = persona.getSangre().getVisitorDonacion(Donacion.Accion.DONAR, tipoDonacion);
		sangre.accept(visitor);
		return visitor.puede();
	}
}
