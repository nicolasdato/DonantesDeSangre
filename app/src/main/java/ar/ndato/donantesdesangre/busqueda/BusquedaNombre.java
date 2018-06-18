package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

public class BusquedaNombre extends BusquedaCondicion {
	private String nombre;
	
	public BusquedaNombre(String nombre, Busqueda busqueda) {
		super(busqueda);
		this.nombre = nombre;
	}
	
	@Override
	protected Boolean condicion(Persona persona) {
		return persona.getNombre().contains(nombre);
	}
}
