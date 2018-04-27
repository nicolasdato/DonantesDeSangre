package ar.ndato.donantesdesangre.sangre;

public class ARhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorARhNDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorARhNRecibirDe();
	}

	@Override
	public String toString() {
		return "A-";
	}
}
