package ar.ndato.donantesdesangre.visitor;

public abstract class VisitorRecibeDe implements VisitorSangre {
	protected Boolean puede;

	public VisitorRecibeDe() {
		puede = false;
	}

	public Boolean puedeRecibir() {
		return puede;
	}
}
