package ar.ndato.donantesdesangre.visitor;

public abstract class VisitorDonaA implements VisitorSangre {
	private Boolean puede;

	public VisitorDonaA() {
		puede = false;
	}

	public Boolean puedeDonar() {
		return puede;
	}
}
