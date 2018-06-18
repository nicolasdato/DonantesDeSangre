package ar.ndato.donantesdesangre.busqueda;

import java.io.Serializable;

import ar.ndato.donantesdesangre.Persona;

/**
 * Clase para realizar busquedas o filtros sobre personas, pudiendo anidarse condiciones excluyentes
 * Por ejemplo se puede crear una busqueda de localidad y provincia:
 * Busqueda busqueda = new BusquedaLocalidad("Bahia Blanca", new BusquedaProvincia("Buenos Aires", new BusquedaBase()));
 * @see BusquedaCondicion
 * @see BusquedaBase
 */
public abstract class Busqueda implements Serializable {
	protected abstract Boolean condicionRecursiva(Persona persona);

	/**
	 * @param persona la {@link Persona} a ver si cumple con las condiciones
	 * @return True si la {@link Persona} cumple con las condiciones
	 */
	public Boolean acepta(Persona persona)	{
		return persona != null && condicionRecursiva(persona);
	}
}
