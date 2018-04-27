package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ORhN;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ORhNFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ORhN();
	}
}
