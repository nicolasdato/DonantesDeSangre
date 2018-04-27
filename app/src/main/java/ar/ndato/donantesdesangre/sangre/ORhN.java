package ar.ndato.donantesdesangre.sangre;

public class ORhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorORhNDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorORhNRecibirDe();
	}

	@Override
	public String toString() {
		return "O-";
	}
}
