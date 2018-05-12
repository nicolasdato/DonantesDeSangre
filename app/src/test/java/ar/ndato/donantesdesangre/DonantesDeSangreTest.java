package ar.ndato.donantesdesangre;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Set;

public class DonantesDeSangreTest {
    private DonantesDeSangre donantesDeSangre;

    @Test
    public void singletonTest() {
        DonantesDeSangre dds = null;
        dds = DonantesDeSangre.getInstancia();
        assertNotNull(dds);
        assertTrue(dds == DonantesDeSangre.getInstancia());
    }

    @Test
    public void donantesDeSangreTest() {
        DonantesDeSangre dds = DonantesDeSangre.getInstancia();
        Persona yo;
        Persona p1;
        Persona p2;
        Persona p3;
        Persona p4;
        Persona p5;
        Set<Persona> donantes, donantesBusqueda;
        Estadistica estadistica;
        Busqueda busquedaEstadistica;
        Busqueda busquedaSangre;

        assertNull(dds.getYo());
        assertNull(dds.getPersonas());

        yo = new Persona("Yo", "l", "p", "d", "t", "m", true, new Date(), new ARhPFactory().crearSangre(), null);
        dds.setYo(yo);
        assertTrue(p == dds.getYo());

        p1 = new Persona("Uno", "l", "p", "d", "t", "m", true, new Date(), new ORhPFactory().crearSangre(), null);
        p2 = new Persona("Dos", "l", "p", "d", "t", "m", false, new Date(), new ABRhPFactory().crearSangre(), null);
        p3 = new Persona("Tres", "L", "p", "d", "t", "m", false, new Date(), new ARhPFactory().crearSangre(), null);
        p4 = new Persona("Cuatro", "L", "p", "d", "t", "m", true, new Date(), new BRhNFactory().crearSangre(), null);
        p5 = new Persona("Cinco", "A", "P", "d", "t", "m", true, new Date(), new ARhPFactory().crearSangre(), null);
        dds.agregarDonante(p1);
        dds.agregarDonante(p2);
        donantes = dds.getDonantes();
        assertNotNull(donantes);
        assertTrue(donantes.contains(p1));
        assertTrue(donantes.contains(p2));
        assertEquals(donantes.size(), 2);
        dds.quitarDonante(p1);
        assertFalse(donantes.contains(p1));
        assertTrue(donantes.contains(p2));
        assertEquals(donantes.size(), 1);
        dds.agregarDonante(p1);
        dds.agregarDonante(p3);
        dds.agregarDonante(p4);
        dds.agregarDonante(p5);

        busquedaEstadistica = new BusquedaEsFavorito(new BusquedaLocalidad("l", new BusquedaBase()));
        estadistica = dds.getEstadistica(busquedaEstadistica);
        assertNotNull(estadistica);
        assertEquals(estadistica.getTotal(), 2);
        assertEquals(estadistica.getAP(), 1);
        assertEquals(estadistica.getAN(), 0);
        assertEquals(estadistica.getBP(), 0);
        assertEquals(estadistica.getBN(), 0);
        assertEquals(estadistica.getABP(), 0);
        assertEquals(estadistica.getABN(), 0);
        assertEquals(estadistica.getOP(), 1);
        assertEquals(estadistica.getON(), 0);
        busquedaSangre = new BusquedaPuedeRecibirDe(dds.getYo().getSangre(), new BusquedaProvincia("p", new BusquedaBase()));
        donantesBusqueda = dds.buscarDonantes(busquedaSangre);
        assertNotNull(donantesBusqueda);
        assertEquals(donantesBusqueda.size(), 3);
        assertTrue(donantesBusqueda.contains(yo));
        assertTrue(donantesBusqueda.contains(p2));
        assertTrue(donantesBusqueda.contains(p3));
    }
}
