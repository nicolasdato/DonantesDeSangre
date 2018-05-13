package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

/**
 * Esta es la busqueda base que corta con la cadena de condiciones, tambien se puede usar como una condicion que siempre da verdadero
 * @see Busqueda
 * @see BusquedaCondicion
 */
public class BusquedaBase extends Busqueda {

	@Override
	protected Boolean condicionRecursiva(Persona persona) {
		return true;
	}
}
