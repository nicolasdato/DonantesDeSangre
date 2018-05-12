package ar.ndato.donantesdesangre.sangre;
import ar.ndato.donantesdesangre.visitor.*;

public class ARhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorARhNDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorARhNRecibeDe();
	}

	@Override
	public String toString() {
		return "A-";
	}
}
