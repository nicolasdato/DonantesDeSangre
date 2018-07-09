package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;

public class VisitorBRhNDonacion extends VisitorDonacion {
	public VisitorBRhNDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		super(accion, tipoDonacion, BN);
	}
}
