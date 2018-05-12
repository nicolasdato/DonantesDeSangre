package ar.ndato.donantesdesangre.sangre;
import ar.ndato.donantesdesangre.visitor.*;

public class ABRhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorABRhNDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorABRhNRecibeDe();
	}

	@Override
	public String toString() {
		return "AB-";
	}
}
