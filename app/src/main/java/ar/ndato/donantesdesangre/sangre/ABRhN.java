package ar.ndato.donantesdesangre.sangre;

public class ABRhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorABRhNDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorABRhNRecibirDe();
	}

	@Override
	public String toString() {
		return "AB-";
	}
}
