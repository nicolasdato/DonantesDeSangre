package ar.ndato.donantesdesangre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

import ar.ndato.donantesdesangre.sangre.Sangre;

/**
 * Clase para modelar los donantes
 */
public class Persona implements Serializable, Comparable<Persona> {
	private String nombre;
	private String localidad;
	private String provincia;
	private String direccion;
	private String telefono;
	private String mail;
	private Boolean favorito;
	private Calendar nacimiento;
	private Sangre sangre;

	public Persona(Persona persona) {
		this.nombre = persona.getNombre();
		this.localidad = persona.getLocalidad();
		this.provincia = persona.getProvincia();
		this.direccion = persona.getDireccion();
		this.telefono = persona.getTelefono();
		this.mail = persona.getMail();
		this.favorito = persona.isFavorito();
		this.nacimiento = persona.getNacimiento();
		this.sangre = persona.getSangre();
	}

	public Persona(String nombre, String localidad, String provincia, String direccion, String telefono, String mail, Boolean favorito, Calendar nacimiento, Sangre sangre) {
		this.nombre = nombre;
		this.localidad = localidad;
		this.provincia = provincia;
		this.direccion = direccion;
		this.telefono = telefono;
		this.mail = mail;
		this.favorito = favorito;
		this.nacimiento = nacimiento;
		this.sangre = sangre;
	}

	@Override
	public boolean equals(Object otro) {
		if (otro == null) return false;
		if (otro == this) return true;
		if (!(otro instanceof Persona)) return false;

		Persona p = (Persona) otro;

		if (p.nombre != null && !p.nombre.equals(nombre)) return false;
		if (p.nombre == null && nombre != null) return false;
		if (p.localidad != null && !p.localidad.equals(localidad)) return false;
		if (p.localidad == null && localidad != null) return false;
		if (p.provincia != null && !p.provincia.equals(provincia)) return false;
		if (p.provincia == null && provincia != null) return false;
		if (p.direccion != null && !p.direccion.equals(direccion)) return false;
		if (p.direccion == null && direccion != null) return false;
		if (p.telefono != null && !p.telefono.equals(telefono)) return false;
		if (p.telefono == null && telefono != null) return false;
		if (p.mail != null && !p.mail.equals(mail)) return false;
		if (p.mail == null && mail != null) return false;
		if (p.favorito != null && !p.favorito.equals(favorito)) return false;
		if (p.favorito == null && favorito != null) return false;
		if (p.nacimiento != null && !p.nacimiento.equals(nacimiento)) return false;
		if (p.nacimiento == null && nacimiento != null) return false;
		if (p.sangre != null && !p.sangre.equals(sangre)) return false;
		if (p.sangre == null && sangre != null) return false;

		return true;
	}
	
	@Override
	public int hashCode() {
		return nombre.hashCode() ^ sangre.hashCode();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}

	public Calendar getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Calendar nacimiento) {
		this.nacimiento = nacimiento;
	}

	public Sangre getSangre() {
		return sangre;
	}

	public void setSangre(Sangre sangre) {
		this.sangre = sangre;
	}
	
	/**
	 * Compara la {@link Persona} para ordenarla. La persona que sea favorita va primera contra una persona que no sea favorita. Si ambas son favoritas o no favoritas por igual, entonces el orden es el de los nombres
	 * @param o la {@link Persona contra la cual comparar}
	 * @return -1 si o es mayor a this, 1 si this es mayor a o, 0 si son iguales
	 */
	@Override
	public int compareTo(@NonNull Persona o) {
		if (this.isFavorito() && !o.isFavorito()) {
			return -1;
		}
		if (!this.isFavorito() && o.isFavorito()) {
			return 1;
		}
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}
}
