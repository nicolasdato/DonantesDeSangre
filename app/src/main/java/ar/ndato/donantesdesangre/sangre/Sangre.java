package ar.ndato.donantesdesangre.sangre;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.visitor.*;

public interface Sangre {
	
	@Override
	boolean equals(Object otro);
	
	@Override
	int hashCode();

	/**
	 * @see VisitorSangre
	 */
	void accept(VisitorSangre visitor);

	/**
	 * @return un nuevo visitor que evaluara la donacion de sangre
	 */
	VisitorDonacion getVisitorDonacion(Donacion.Accion Accion, Donacion.TipoDonacion tipoDonacion);
	
}
