package ar.ndato.donantesdesangre.sangre;

public class ABRhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorABRhPDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorABRhPRecibeDe();
	}

	@Override
	public String toString() {
		return "AB+";
	}
}
