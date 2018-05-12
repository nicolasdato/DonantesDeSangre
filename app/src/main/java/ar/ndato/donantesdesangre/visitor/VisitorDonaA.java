package ar.ndato.donantesdesangre.visitor;

public abstract class VisitorDonaA implements VisitorSangre {
	protected Boolean puede;

	public VisitorDonaA() {
		puede = false;
	}

	public Boolean puedeDonar() {
		return puede;
	}
}
