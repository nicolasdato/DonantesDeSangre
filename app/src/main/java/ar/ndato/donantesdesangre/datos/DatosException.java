package ar.ndato.donantesdesangre.datos;

/**
 * Exception para encapsular errores con la lectura y guardado de datos
 * @see Datos#guardar
 * @see Datos#leer
 */
public class DatosException extends Exception {

	public DatosException(String msg) {
		super(msg);
	}
}
