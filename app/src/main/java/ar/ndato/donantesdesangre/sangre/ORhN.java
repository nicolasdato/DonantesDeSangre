package ar.ndato.donantesdesangre.sangre;

public class ORhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorORhNDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorORhNRecibeDe();
	}

	@Override
	public String toString() {
		return "O-";
	}
}
