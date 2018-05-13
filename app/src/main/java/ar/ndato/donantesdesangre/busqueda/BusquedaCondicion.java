package ar.ndato.donantesdesangre.busqueda;

import ar.ndato.donantesdesangre.Persona;

/**
 * Las clases con las diferentes condiciones deben de heredar de esta e implementar el metodo {@link BusquedaCondicion#condicion}
 * @see Busqueda
 * @see BusquedaBase
 */
public abstract class BusquedaCondicion extends Busqueda {

	private Busqueda busqueda;

	/**
	 * Se pueden agregar mas condiciones pasandolos al constructor
	 * @param busqueda la {@link Busqueda} de otra condicion a cumplir, o si no hay mas condiciones se puede usar null o {@link BusquedaBase}
	 */
	protected BusquedaCondicion(Busqueda busqueda){
		this.busqueda = busqueda;
		if(this.busqueda == null) {
			this.busqueda = new BusquedaBase();
		}
	}

	/**
	 * Este metodo es el que las condiciones deben implementar con la condicion de aceptar o no la persona
	 * @return True si la {@link Persona} es aceptada por esta condicion
	 */
	protected abstract Boolean condicion(Persona persona);

	@Override
	protected final Boolean condicionRecursiva(Persona persona){
		return persona != null && condicion(persona) && busqueda.condicionRecursiva(persona);
	}
}
