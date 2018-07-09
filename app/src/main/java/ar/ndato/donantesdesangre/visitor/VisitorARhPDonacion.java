package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;

public class VisitorARhPDonacion extends VisitorDonacion {
	public VisitorARhPDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion) {
		super(accion, tipoDonacion, AP);
	}
}
