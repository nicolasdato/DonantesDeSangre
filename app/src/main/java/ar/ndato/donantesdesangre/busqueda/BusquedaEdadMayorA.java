package ar.ndato.donantesdesangre.busqueda;

import java.util.Calendar;

import ar.ndato.donantesdesangre.Persona;

public class BusquedaEdadMayorA extends BusquedaCondicion {

	private Integer edad;

	public BusquedaEdadMayorA(Integer edad, Busqueda busqueda) {
		super(busqueda);
		this.edad = edad;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		Calendar hoy = Calendar.getInstance();
		Calendar nacimiento = persona.getNacimiento();

		hoy.add(Calendar.DAY_OF_MONTH, -nacimiento.get(Calendar.DAY_OF_MONTH));
		hoy.add(Calendar.MONTH, -nacimiento.get(Calendar.MONTH));
		hoy.add(Calendar.YEAR, -nacimiento.get(Calendar.YEAR));

		return hoy.get(Calendar.YEAR) >= edad;
	}
}
