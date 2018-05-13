package ar.ndato.donantesdesangre;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ar.ndato.donantesdesangre.sangre.Sangre;

/**
 * Clase para modelar los donantes
 */
public class Persona {
	private String nombre;
	private String localidad;
	private String provincia;
	private String direccion;
	private String telefono;
	private String mail;
	private Boolean favorito;
	private Calendar nacimiento;
	private Set<Donacion> donaciones;
	private Sangre sangre;

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

		donaciones = new HashSet<Donacion>();
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
		if (p.donaciones != null && !p.donaciones.equals(donaciones)) return false;
		if (p.donaciones == null && donaciones != null) return false;
		if (p.sangre != null && !p.sangre.equals(sangre)) return false;
		if (p.sangre == null && sangre != null) return false;

		return true;
	}

	/**
	 * Agrega una nueva donacion si no existe ya
	 *
	 * @param donacion la {@link Donacion} a agregar
	 * @see Persona#quitarDonacion
	 * @see Persona#getDonaciones
	 */
	public void agregarDonacion(Donacion donacion) {
		if (donacion != null) {
			donaciones.add(donacion);
		}
	}

	/**
	 * Quita la donacion
	 *
	 * @param donacion la {@link Donacion} a quitar
	 * @see Persona#agregarDonacion
	 * @see Persona#getDonaciones
	 */
	public void quitarDonacion(Donacion donacion) {
		donaciones.remove(donacion);
	}

	/**
	 * Devuelve las donaciones realizadas por esta persona
	 *
	 * @return Un Set no modificable de {@link Donacion}, las donaciones se pueden modificar pero no el Set
	 * @note retorna un Set no modificable (Collections.unmidifiableSet()}
	 * @see Persona#agregarDonacion
	 * @see Persona#quitarDonacion
	 */
	public Set<Donacion> getDonaciones() {
		return Collections.unmodifiableSet(donaciones);
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
}
