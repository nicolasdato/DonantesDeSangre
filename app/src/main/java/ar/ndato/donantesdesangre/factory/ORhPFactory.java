package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ORhP;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ORhPFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ORhP();
	}
}
