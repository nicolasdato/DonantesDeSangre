package ar.ndato.donantesdesangre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Donaciones que se usan en {@link DonantesDeSangre}
 */
public class Donacion implements Serializable, Comparable<Donacion> {
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
	
	@Override
	public int hashCode() {
		int hash = 0;
		if (getReceptor() != null) {
			hash ^= getReceptor().hashCode();
		}
		return hash ^ getFecha().hashCode();
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
	
	/**
	 * Compara 2 {@link Donacion} segun su fecha, si es igual la fecha compara los receptores receptor ({@link Persona})
	 */
	@Override
	public int compareTo(@NonNull Donacion o) {
		if (this.getFecha().after(o.getFecha())) {
			return -1;
		}
		if (this.getFecha().before(o.getFecha())) {
			return 1;
		}
		if (this.getReceptor() != null) {
			return this.getReceptor().compareTo(o.getReceptor());
		}
		return 0;
	}
}
