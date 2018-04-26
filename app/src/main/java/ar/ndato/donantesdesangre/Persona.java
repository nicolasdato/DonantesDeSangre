package ar.ndato.donantesdesangre;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Date nacimiento;
    private Set<Donacion> donaciones;

    public Persona(String nombre, String localidad, String provincia, String direccion, String telefono, String mail, Boolean favorito, Date nacimiento) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.provincia = provincia;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.favorito = favorito;
        this.nacimiento = nacimiento;

        donaciones = new HashSet<Donacion>();
    }

    @Override
    public boolean equals(Object otro) {
        if(otro == null) return false;
        if(otro == this) return true;
        if(!(otro instanceof Persona)) return false;

        Persona persona = (Persona)otro;

        if(!persona.getNombre().equals(nombre)) return false;
        if(!persona.getLocalidad().equals(localidad)) return false;
        if(!persona.getProvincia().equals(provincia)) return false;
        if(!persona.getDireccion().equals(direccion)) return false;
        if(!persona.getTelefono().equals(telefono)) return false;
        if(!persona.getMail().equals(mail)) return false;
        if(!persona.getFavorito().equals(favorito)) return false;
        if(!persona.getNacimiento().equals(nacimiento)) return false;
        if(!persona.getDonaciones().equals(donaciones)) return false;

        return true;
    }

    /**
     * Agrega una nueva donacion si no existe ya
     * @param donacion la {@link Donacion} a agregar
     * @see quitarDonacion()
     * @see getDonaciones()
     */
    public void agregarDonacion(Donacion donacion) {
        if(donacion != null){
            donaciones.add(donacion);
        }
    }

    /**
     * Quita la donacion
     * @param donacion la {@link Donacion} a quitar
     * @see agregarDonacion()
     * @see getDonaciones()
     */
    public void quitarDonacion(Donacion donacion) {
        donaciones.remove(donacion);
    }

    /**
     * Devuelve las donaciones realizadas por esta persona
     * @note retorna un Set no modificable (Collections.unmidifiableSet()}
     * @return Un Set no modificable de {@link Donacion}, las donaciones se pueden modificar pero no el Set
     * @see agregarDonacion()
     * @see quitarDonacion()
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

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
}
