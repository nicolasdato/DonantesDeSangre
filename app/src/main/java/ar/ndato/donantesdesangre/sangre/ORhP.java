package ar.ndato.donantesdesangre.sangre;

public class ORhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorORhPDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorORhPRecibeDe();
	}

	@Override
	public String toString() {
		return "O+";
	}
}
