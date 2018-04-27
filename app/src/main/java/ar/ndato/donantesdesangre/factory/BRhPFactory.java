package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.BRhP;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class BRhPFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new BRhP();
	}
}
