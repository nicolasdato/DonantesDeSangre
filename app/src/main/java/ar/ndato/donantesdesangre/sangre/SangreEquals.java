package ar.ndato.donantesdesangre.sangre;

import java.io.Serializable;

public abstract class SangreEquals implements Sangre, Serializable {
	
	public abstract String toString();

	@Override
	public boolean equals(Object otro) {
		if(otro == null) return false;
		if(otro == this) return true;
		if(!(otro instanceof Sangre)) return false;

		return toString().equals(otro.toString());
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
