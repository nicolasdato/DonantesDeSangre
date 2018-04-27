package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.ARhN;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class ARhNFactory implements AbstractSangreFactory {

	@Override
	public Sangre crearSangre() {
		return new ARhN();
	}
}
