package ar.ndato.donantesdesangre.datos;

import java.util.Set;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.DonantesDeSangre;

/**
 * Interface para el guardardo y la lectura de datos, lo usa {@link DonantesDeSangre}
 */
public interface Datos {

	/**
	 * Carga los datos y los deja disponibles para luego usar {@link Datos#getYo} y {@link Datos#getDonantes}
	 * @see Datos#guardar
	 * @see Datos#getYo
	 * @see Datos#getDonantes
	 */
	void leer();

	/**
	 * @see Datos#leer
	 */
	void guardar(Persona yo, Set<Persona> donantes);

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
}
