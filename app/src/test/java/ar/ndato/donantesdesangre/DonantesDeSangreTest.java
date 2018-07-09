package ar.ndato.donantesdesangre;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaEsFavorito;
import ar.ndato.donantesdesangre.busqueda.BusquedaLocalidad;
import ar.ndato.donantesdesangre.busqueda.BusquedaProvincia;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.factory.ABRhPFactory;
import ar.ndato.donantesdesangre.factory.ARhPFactory;
import ar.ndato.donantesdesangre.factory.BRhNFactory;
import ar.ndato.donantesdesangre.factory.ORhPFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DonantesDeSangreTest {
    private DonantesDeSangre donantesDeSangre;

    @Test
    public void singletonTest() {
        DonantesDeSangre dds = null;
        dds = DonantesDeSangre.getInstance();
        assertNotNull(dds);
        assertTrue(dds == DonantesDeSangre.getInstance());
    }

    @Test
    public void donantesDeSangreTest() {
        DonantesDeSangre dds = DonantesDeSangre.getInstance();
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
        assertEquals(dds.getDonantes(), new HashSet<Persona>());

        yo = new Persona("Yo", "l", "p", "d", "t", "m", true, new GregorianCalendar(), new ARhPFactory().crearSangre());
        dds.setYo(yo);
        assertTrue(yo == dds.getYo());

        p1 = new Persona("Uno", "l", "p", "d", "t", "m", true, new GregorianCalendar(), new ORhPFactory().crearSangre());
        p2 = new Persona("Dos", "l", "p", "d", "t", "m", false, new GregorianCalendar(), new ABRhPFactory().crearSangre());
        p3 = new Persona("Tres", "D", "p", "d", "t", "m", false, new GregorianCalendar(), new ARhPFactory().crearSangre());
        p4 = new Persona("Cuatro", "D", "p", "d", "t", "m", true, new GregorianCalendar(), new BRhNFactory().crearSangre());
        p5 = new Persona("Cinco", "A", "J", "d", "t", "m", true, new GregorianCalendar(), new ARhPFactory().crearSangre());
        dds.agregarDonante(p1);
        dds.agregarDonante(p2);
        donantes = dds.getDonantes();
        assertNotNull(donantes);
        assertTrue(donantes.contains(p1));
        assertTrue(donantes.contains(p2));
        assertTrue(donantes.contains(yo));
        assertEquals(donantes.size(), 3);
        dds.quitarDonante(p1);
        assertFalse(donantes.contains(p1));
        assertTrue(donantes.contains(p2));
        assertEquals(donantes.size(), 2);
        dds.agregarDonante(p1);
        dds.agregarDonante(p3);
        dds.agregarDonante(p4);
        dds.agregarDonante(p5);

        busquedaEstadistica = new BusquedaEsFavorito(new BusquedaLocalidad("l", new BusquedaBase()));
        estadistica = dds.getEstadistica(busquedaEstadistica);
        assertNotNull(estadistica);
        assertTrue(estadistica.getTotal() == 2);
        assertTrue(estadistica.getAp() == 1);
        assertTrue(estadistica.getAn() == 0);
        assertTrue(estadistica.getBp() == 0);
        assertTrue(estadistica.getBn() == 0);
        assertTrue(estadistica.getAbp() == 0);
        assertTrue(estadistica.getAbn() == 0);
        assertTrue(estadistica.getOp() == 1);
        assertTrue(estadistica.getOn() == 0);
        busquedaSangre = new BusquedaPuedeRecibirDe(dds.getYo().getSangre(), Donacion.TipoDonacion.GLOBULOS_ROJOS, new BusquedaProvincia("p", new BusquedaBase()));
        donantesBusqueda = dds.buscarDonantes(busquedaSangre);
        assertNotNull(donantesBusqueda);
        assertEquals(donantesBusqueda.size(), 3);
        assertTrue(donantesBusqueda.contains(yo));
        assertTrue(donantesBusqueda.contains(p2));
        assertTrue(donantesBusqueda.contains(p3));

        Donacion d1 = new Donacion(p2, Calendar.getInstance(), Donacion.TipoDonacion.GLOBULOS_ROJOS);
        Donacion d2 = new Donacion(p3, Calendar.getInstance(), Donacion.TipoDonacion.GLOBULOS_ROJOS);
        Donacion d3 = new Donacion(p1, Calendar.getInstance(), Donacion.TipoDonacion.GLOBULOS_ROJOS);
        dds.agregarDonacion(p1, d1);
        dds.agregarDonacion(p1, d2);
        dds.agregarDonacion(p3, d3);

        Set<Donacion> setD1 = new HashSet<Donacion>();
        setD1.add(d1);
        setD1.add(d2);
        Set<Donacion> setD3 = new HashSet<Donacion>();
        setD3.add(d3);
        assertEquals(dds.getDonaciones(p1), setD1);
        assertEquals(dds.getDonaciones(p3), setD3);
        assertEquals(dds.getDonaciones(p2), new HashSet<Donacion>());
        assertEquals(dds.getDonaciones(p4), new HashSet<Donacion>());

	    /*try {
		    dds.exportar(new DatosJson(new File("test.json")));
		    Datos datos = new DatosJson(new File("test.json"));
			datos.leer();
			assertEquals(datos.getYo(), dds.getYo());
			assertEquals(datos.getDonantes(), dds.getDonantes());
			for(Persona persona : datos.getDonantes()) {
				assertEquals(datos.getDonaciones(persona), dds.getDonaciones(persona));
			}
	    } catch (DatosException e) {
		    assertFalse(true);
	    }*/

    }
}
