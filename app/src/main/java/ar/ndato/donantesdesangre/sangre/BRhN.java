package ar.ndato.donantesdesangre.sangre;

public class BRhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorBRhNDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorBRhNRecibirDe();
	}

	@Override
	public String toString() {
		return "B-;
	}
}
