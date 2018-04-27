package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ABRhP;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ABRhPFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ABRhP();
	}
}
