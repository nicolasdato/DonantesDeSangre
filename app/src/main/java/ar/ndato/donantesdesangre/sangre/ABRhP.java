package ar.ndato.donantesdesangre.sangre;

public class ABRhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorABRhPDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorABRhPRecibirDe();
	}

	@Override
	public String toString() {
		return "AB+";
	}
}
