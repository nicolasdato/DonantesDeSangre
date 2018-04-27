package ar.ndato.donantesdesangre.sangre;

public class ARhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonarA getVisitorDonarA() {
		return new VisitorARhPDonarA();
	}

	@Override
	public VisitorRecibirDe getVisitorRecibirDe() {
		return new VisitorARhPRecibirDe();
	}

	@Override
	public String toString() {
		return "A+";
	}
}
