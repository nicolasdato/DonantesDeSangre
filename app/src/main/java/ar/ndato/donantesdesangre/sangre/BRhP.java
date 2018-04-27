package ar.ndato.donantesdesangre.sangre;

public class BRhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorBRhPDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorBRhPRecibirDe();
	}

	@Override
	public String toString() {
		return "B+";
	}
}
