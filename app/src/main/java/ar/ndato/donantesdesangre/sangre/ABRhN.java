package ar.ndato.donantesdesangre.sangre;
import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.visitor.*;

public class ABRhN extends SangreEquals {

	@Override
	public void accept(VisitorSangre visitor) {
		if(visitor != null) {
			visitor.visit(this);
		}
	}

	@Override
	public VisitorDonacion getVisitorDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		return new VisitorABRhNDonacion(accion, tipoDonacion);
	}

	@Override
	public String toString() {
		return "AB-";
	}
}
