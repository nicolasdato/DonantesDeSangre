package ar.ndato.donantesdesangre.sangre;

public class BRhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorBRhPDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorBRhPRecibeDe();
	}

	@Override
	public String toString() {
		return "B+";
	}
}
