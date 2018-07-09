package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;

public class VisitorORhNDonacion extends VisitorDonacion {
	public VisitorORhNDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		super(accion, tipoDonacion, ON);
	}
}
