package ar.ndato.donantesdesangre.sangre;

public class ORhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorORhPDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorORhPRecibirDe();
	}

	@Override
	public String toString() {
		return "O+";
	}
}
