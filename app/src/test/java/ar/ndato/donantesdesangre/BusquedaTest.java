package ar.ndato.donantesdesangre;

import org.junit.Test;

import java.util.GregorianCalendar;

import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMayorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMenorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEsFavorito;
import ar.ndato.donantesdesangre.busqueda.BusquedaLocalidad;
import ar.ndato.donantesdesangre.busqueda.BusquedaProvincia;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeDonarA;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.factory.ABRhPFactory;
import ar.ndato.donantesdesangre.factory.ARhNFactory;
import ar.ndato.donantesdesangre.factory.ARhPFactory;
import ar.ndato.donantesdesangre.factory.BRhPFactory;
import ar.ndato.donantesdesangre.factory.ORhNFactory;
import ar.ndato.donantesdesangre.factory.ORhPFactory;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BusquedaTest {

	@Test
	public void busquedaTest() {
		Persona persona;
		Busqueda busqueda;

		persona = new Persona("Nombre", "Localidad", "Provincia",
				"Direccion", "Telefono", "mail", false,
				new GregorianCalendar(1991, GregorianCalendar.DECEMBER, 8), new ARhPFactory().crearSangre());

		busqueda = new BusquedaEdadMenorA(99, null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaEdadMenorA(20, null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaEdadMayorA(20, null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaEdadMayorA(99, null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaLocalidad("Localidad", new BusquedaProvincia("Provincia", null));
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaLocalidad("NoLocalidad", new BusquedaProvincia("Provincia", null));
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaLocalidad("Localidad", new BusquedaProvincia("NoProvincia", null));
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaLocalidad("NoLocalidad", new BusquedaProvincia("NoProvincia", null));
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaEsFavorito(new BusquedaProvincia("Provincia", null));
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeRecibirDe(new ORhNFactory().crearSangre(), new BusquedaLocalidad("Localidad", new BusquedaProvincia("Provincia", new BusquedaEdadMayorA(10, null))));
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeRecibirDe(new ARhNFactory().crearSangre(), null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeRecibirDe(new ORhPFactory().crearSangre(), null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeRecibirDe(new BRhPFactory().crearSangre(), null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeRecibirDe(new ABRhPFactory().crearSangre(), null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeDonarA(new ABRhPFactory().crearSangre(), null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeDonarA(new ARhPFactory().crearSangre(), null);
		assertTrue(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeDonarA(new ARhNFactory().crearSangre(), null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaPuedeDonarA(new ORhNFactory().crearSangre(), null);
		assertFalse(busqueda.acepta(persona));
		busqueda = new BusquedaBase();
		assertTrue(busqueda.acepta(persona));
	}

}
