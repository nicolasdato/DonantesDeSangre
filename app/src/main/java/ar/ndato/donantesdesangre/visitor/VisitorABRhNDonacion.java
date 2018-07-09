package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;

public class VisitorABRhNDonacion extends VisitorDonacion {
	public VisitorABRhNDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		super(accion, tipoDonacion, ABN);
	}
}
