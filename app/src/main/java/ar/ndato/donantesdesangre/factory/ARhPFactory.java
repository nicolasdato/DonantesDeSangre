package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ARhP;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ARhPFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ARhP();
	}
}
