package ar.ndato.donantesdesangre.factory;

import ar.ndato.donantesdesangre.sangre.Sangre;

public class SangreStringFactory implements AbstractSangreFactory {

	AbstractSangreFactory factory;

	public SangreStringFactory(String sangre) {
		if(sangre.equals("A+")) {
			factory = new ARhPFactory();
		}
		if(sangre.equals("B+")) {
			factory = new BRhPFactory();
		}
		if(sangre.equals("AB+")) {
			factory = new ABRhPFactory();
		}
		if(sangre.equals("O+")) {
			factory = new ORhPFactory();
		}
		if(sangre.equals("A-")) {
			factory = new ARhNFactory();
		}
		if(sangre.equals("B-")) {
			factory = new BRhNFactory();
		}
		if(sangre.equals("AB-")) {
			factory = new ABRhNFactory();
		}
		if(sangre.equals("O-")) {
			factory = new ORhNFactory();
		}
	}

	@Override
	public Sangre crearSangre() {
		return factory.crearSangre();
	}
}
