package ar.ndato.donantesdesangre;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ar.ndato.donantesdesangre.visitor.VisitorEstadistica;

/**
 * Clase principal para acceder y manejar todos los donantes y donaciones, usar {@link DonantesDeSangre#getInstance()} para obtener la instancia
 */
public class DonantesDeSangre {
    private static DonantesDeSangre instancia;

    /**
     * Yo esta incluido dentro de {@link DonantesDeSangre#getDonantes}
     */
    private Persona yo;
    private Set<Persona> donantes;
    /**
     * Usar {@link DonantesDeSangre#getInstance()} para obtener la instancia
     * @see DonantesDeSangre#getInstance()
     */
    private DonantesDeSangre() {
        donantes = new HashSet<>();
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
     * Agrega un nuevo donante, si ya existe. Se puede quitar con {@link DonantesDeSangre#quitarDonante}.
     * @param persona el donante a agregar
     * @see DonantesDeSangre#quitarDonante
     * @see DonantesDeSangre#getDonantes()
     */
    public void agregarDonante(Persona persona) {
        if(persona != null) {
            donantes.add(persona);
        }
    }

    /**
     * Quita un donante agregado con {@link DonantesDeSangre#agregarDonante}
     * @param persona el donante a quitar
     * @see DonantesDeSangre#agregarDonante
     * @see DonantesDeSangre#getYo()
     * @see DonantesDeSangre#getDonantes()
     *
     */
    public void quitarDonante(Persona persona) {
        donantes.remove(persona);
    }

    /**
     * Retorna la lista de personas.
     * Para saber cual es Yo, usar {@link DonantesDeSangre#getYo()}
     * @note retorna un Set no modificable (Collections.unmidifiableSet()}
     * @returns Un Set no modificable de {@link Persona}, las personas se pueden modificar pero no el Set
     * @see DonantesDeSangre#agregarDonante
     * @see DonantesDeSangre#quitarDonante
     * @see DonantesDeSangre#getYo()
     * @see Persona
     */
    public Set<Persona> getDonantes() {
        return Collections.unmodifiableSet(donantes);
    }

    /**
     * La {@link Persona} Yo tambien esta en {@link DonantesDeSangre#getDonantes()}
     * @return la persona asociada a Yo, o null si no fue asignada aun
     * @see DonantesDeSangre#setYo
     * @see DonantesDeSangre#getDonantes
     */
    public Persona getYo() {
        return yo;
    }

    /**
     * Asocia la persona a Yo. Si persona no esta en donantes es agregada.
     * @param persona
     * @see DonantesDeSangre#getYo
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
     * Busca dentro de los donantes agregados con {@link DonantesDeSangre#agregarDonante} los que apliquen al criterio de busqueda
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
     * Reemplaza todos los datos por los nuevos. Para mezclar los datos usar {@link DonantesDeSangre#mezclar}. Para exportar los datos usar {@link DonantesDeSangre#exportar}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see DonantesDeSangre#mezclar
     * @see DonantesDeSangre#exportar
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
     * Guarda los datos. Para mezclar los datos usar {@link DonantesDeSangre#mezclar}. Para importar los datos usar {@link DonantesDeSangre#importar}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see DonantesDeSangre#mezclar
     * @see DonantesDeSangre#importar
     */
    public void exportar(Datos datos) {
        if(datos != null) {
            datos.guardar(yo, donantes);
        }
    }

    /**
     * Mezcla los datos importados con los ya existentes. Para reemplazar los datos usar {@link DonantesDeSangre#importar}. Para exportar los datos usar {@link DonantesDeSangre#exportar}
     * @param datos de donde obtener los nuevos datos
     * @see Datos
     * @see DonantesDeSangre#importar
     * @see DonantesDeSangre#exportar
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
