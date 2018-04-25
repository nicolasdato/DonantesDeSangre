package ar.ndato.donantesdesangre;

import java.util.Collections;
import java.util.Set;

/**
 * Clase principal para acceder y manejar todos los donantes y donaciones, usar {@link getInstance()} para obtener la instancia
 */
public class DonantesDeSangre {
    private static DonantesDeSangre instancia;

    private Persona yo;
    private Set<Persona> donantes;
    /**
     * Usar {@link getInstance()} para obtener la instancia
     * @see getInstance()
     */
    private DonantesDeSangre() {
        donantes = new HashSet<Persona>();
    }

    /**
     * @return Unica instancia de la clase
     */
    static public DonantesDeSangre getInstance() {
        if(instancia == null){
            instancia = new DonantesDeSangre();
        }
        return instancia;
    }

    /**
     * Agrega un nuevo donante, si ya existe. Se puede quitar con {@link quitarDonante()}.
     * @param persona el donante a agregar
     * @see quitarDonante()
     * @see getDonantes()
     */
    public void agregarDonante(Persona persona) {
        if(persona != null) {
            donantes.add(persona);
        }
    }

    /**
     * Quita un donante agregado con {@link agregarDonante()}
     * @param persona el donante a quitar
     * @see agregarDonante()
     * @see getYo()
     * @see getDonantes()
     *
     */
    public void quitarDonante(Persona persona) {
        donantes.remove(persona);
    }

    /**
     * Retorna la lista de personas.
     * Para saber cual es Yo, usar {@link getYo()}
     * @note retorna un Set no modificable (Collections.unmidifiableSet()}
     * @returns Un Set no modificable de {@link Persona}, las personas se pueden modificar pero no el Set
     * @see agregarDonante()
     * @see quitarDonante()
     * @see getYo()
     * @see Persona
     */
    public Set<Persona> getDonantes() {
        return Collections.unmodifiableSet(donantes);
    }

    /**
     * La {@link Persona} Yo tambien esta en {@link getDonantes()}
     * @return la persona asociada a Yo, o null si no fue asignada aun
     * @see setYo()
     * @see getDonantes()
     */
    public Persona getYo() {
        return yo;
    }

    /**
     * Asocia la persona a Yo. Si persona no esta en donantes es agregada.
     * @param persona
     * @see getYo()
     */
    public void setYo(Persona persona) {
        if(persona != null) {
            donantes.add(persona);
            yo = persona;
        }
    }

    /**
     * Se arma una {@link Estadistica} dado el criterio de {@link Busqueda}
     * @param busqueda el criterio de busqueda, si es null se aplica a todos los donantes
     * @return la {@link Estadistica} sobre las personas que aplican al criterio de busqueda
     * @see Estadistica
     * @see Busqueda
     */
    public Estadistica getEstadistica(Busqueda busqueda) {
        VisitorEstadistica visitor = new VisitorEstadistica();

        for(Persona p : donantes) {
            if(busqueda == null || busqueda.acepta(p)){
                p.getSangre().accept(visitor);
            }
        }

        return visitor.getEstadistica();
    }

    /**
     * Busca dentro de los donantes agregados con {@link agregarDonante()} los que apliquen al criterio de busqueda
     * @param busqueda el criterio de busqueda a aplicar
     * @return Un Set de {@link Persona} que cumplieron el criterio de busqueda
     * @see Persona
     * @see Busqueda
     */
    public Set<Persona> buscarDonantes(Busqueda busqueda) {
        Set<Persona> resultado = new HashSet<Persona>();

        for(Persona p : donantes) {
            if(busqueda == null || busqueda.acepta(p)){
                resultado.add(p);
            }
        }

        return resultado;
    }

    /**
     * Reemplaza todos los datos por los nuevos. Para mezclar los datos usar {@link mezclar()}. Para exportar los datos usar {@link exportar()}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see mezclar()
     * @see exportar()
     */
    public void importar(Datos datos) {
        if(datos != null) {
            datos.leer();
            donantes = datos.getDonantes();
            yo = datos.getYo();
            donantes.add(yo);
        }
    }

    /**
     * Guarda los datos. Para mezclar los datos usar {@link mezclar()}. Para importar los datos usar {@link importar()}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see mezclar()
     * @see importar()
     */
    public void exportar(Datos datos) {
        if(datos != null) {
            datos.guardar(yo, donantes);
        }
    }

    /**
     * Mezcla los datos importados con los ya existentes. Para reemplazar los datos usar {@link importar()}. Para exportar los datos usar {@link exportar()}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see importar()
     * @see exportar()
     */
    public void mezclar(Datos datos) {
        if(datos != null) {
            datos.leer();
            donantes.addAll(datos.getDonantes());
            donantes.add(datos.getYo());
            if(yo == null)
                yo = datos.getYo();
        }
    }

}
