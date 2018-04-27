package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ABRhN;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ABRhNFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ABRhN();
	}
}
