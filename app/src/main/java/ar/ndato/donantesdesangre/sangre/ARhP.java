package ar.ndato.donantesdesangre.sangre;

public class ARhP extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonaA getVisitorDonaA() {
		return new VisitorARhPDonaA();
	}

	@Override
	public VisitorRecibeDe getVisitorRecibeDe() {
		return new VisitorARhPRecibeDe();
	}

	@Override
	public String toString() {
		return "A+";
	}
}
