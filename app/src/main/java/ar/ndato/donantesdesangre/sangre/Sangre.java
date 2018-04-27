package ar.ndato.donantesdesangre.sangre;

public interface Sangre {

	@Override
	boolean equals(Object otro);

	/**
	 * @see VisitorSangre
	 */
	void accept(VisitorSangre visitor);

	/**
	 * @return un nuevo visitor que evaluara si esta sangre puede donarse a alguien con la sangre de destino
	 */
	VisitorDonarA getVisitorDonarA();

	/**
	 * @return un nuevo visitor que evaluara si esta sangre puede recibir a la sangre de destino
	 */
	VisitorRecibirDe getVisitorRecibirDe();
}
