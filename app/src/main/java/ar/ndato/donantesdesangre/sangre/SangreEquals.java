package ar.ndato.donantesdesangre.sangre;

public abstract class SangreEquals implements Sangre {

	@Override
	public abstract String toString();

	@Override
	public boolean equals(Object otro) {
		if(otro == null) return false;
		if(otro == this) return true;
		if(!(otro instanceof Sangre)) return false;

		return toString().equals(otro);
	}
}
