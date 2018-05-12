package ar.ndato.donantesdesangre.sangre;
import ar.ndato.donantesdesangre.visitor.*;

public class BRhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorBRhNDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorBRhNRecibeDe();
	}

	@Override
	public String toString() {
		return "B-";
	}
}
