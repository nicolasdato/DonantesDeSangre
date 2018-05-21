package ar.ndato.donantesdesangre.datos;

import java.util.Map;
import java.util.Set;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.Persona;

/**
 * Interface para el guardardo y la lectura de datos, lo usa {@link DonantesDeSangre}
 */
public interface Datos {

	/**
	 * Carga los datos y los deja disponibles para luego usar {@link Datos#getYo} y {@link Datos#getDonantes}
	 * @see Datos#guardar
	 * @see Datos#getYo
	 * @see Datos#getDonantes
	 * @throws DatosException cuando hubo una excepcion al leer los datos (ej, el archivo no existe)
	 */
	void leer() throws DatosException;

	/**
	 * @see Datos#leer
	 * @throws DatosException cuando hubo una excepcion al guardar los datos (ej, no hay mas espacio)
	 */
	void guardar(Persona yo, Map<Persona, Set<Donacion>> donantes) throws DatosException;

	/**
	 * Utilizar una vez llamado a {@link Datos#leer}
	 * @see Datos#leer
	 */
	Persona getYo();

	/**
	 * Utilizar una vez llamado a {@link Datos#leer}
	 * @see Datos#leer
	 */
	Set<Persona> getDonantes();

	/**
	 * Utilizar una vez llamado a {@link Datos#leer}
	 * @param persona la {@link Persona} a la cual obtener sus donaciones
	 * @return las donaciones de la {@link Persona}
	 */
	Set<Donacion> getDonaciones(Persona persona);
}
