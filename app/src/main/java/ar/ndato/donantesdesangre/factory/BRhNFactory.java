package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.BRhN;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class BRhNFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new BRhN();
	}
}
