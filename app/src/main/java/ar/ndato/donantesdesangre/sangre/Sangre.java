package ar.ndato.donantesdesangre.sangre;

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
	 * @return un nuevo visitor que evaluara si esta sangre puede donarse a alguien con la sangre de destino
	 */
	VisitorDonaA getVisitorDonaA();

	/**
	 * @return un nuevo visitor que evaluara si esta sangre puede recibir a la sangre de destino
	 */
	VisitorRecibeDe getVisitorRecibeDe();
}
