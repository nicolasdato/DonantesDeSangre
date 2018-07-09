package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;

public class VisitorORhPDonacion extends VisitorDonacion {
	public VisitorORhPDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		super(accion, tipoDonacion, OP);
	}
}
