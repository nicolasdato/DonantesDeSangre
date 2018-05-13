package ar.ndato.donantesdesangre;

import java.util.Calendar;

/**
 * Donaciones que se usan en {@link Persona}
 */
public class Donacion {
    private Persona receptor;
	private Calendar fecha;

	public Donacion(Persona receptor, Calendar fecha) {
		this.receptor = receptor;
		this.fecha = fecha;
	}

	@Override
	public boolean equals(Object otro) {
		if(otro == null) return false;
		if(otro == this) return true;
		if(!(otro instanceof Donacion)) return false;

		Donacion d = (Donacion)otro;

		if(d.fecha != null && !d.fecha.equals(fecha)) return false;
		if(d.fecha == null && fecha != null) return false;
		if(d.receptor != null && !d.receptor.equals(receptor)) return false;
		if(d.receptor == null && receptor != null) return false;

		return true;
	}

	/**
	 * @return la {@link Persona} que recibio la donacion de sangre
	 * @see Donacion#setReceptor
	 */
	public Persona getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor la {@link Persona} que recibio la donacion
	 * @see Donacion#getReceptor
	 */
	public void setReceptor(Persona receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return la fecha que se realizo la donacion
	 * @see Donacion#setFecha
	 */
	public Calendar getFecha() {
		return fecha;
	}

	/**
	 * @param fecha la fecha que se realizo la donacion
	 * @see Donacion#getFecha
	 */
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
}
