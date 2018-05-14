package ar.ndato.donantesdesangre;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import ar.ndato.donantesdesangre.factory.ARhPFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PersonaTest {
    @Test
    public void personaTest() {
        Persona p, pigual, pdiferente;
        Calendar nacimiento = new GregorianCalendar();
        Sangre sangre = new ARhPFactory().crearSangre();
        Set<Donacion> donaciones;
        Donacion d;

        p = new Persona("P", "l", "p", "d", "t", "m", true, nacimiento, sangre);
        assertEquals(p.getNombre(), "P");
        assertEquals(p.getLocalidad(), "l");
        assertEquals(p.getProvincia(), "p");
        assertEquals(p.getDireccion(), "d");
        assertEquals(p.getTelefono(), "t");
        assertEquals(p.getMail(), "m");
        assertTrue(p.isFavorito());
        assertEquals(p.getNacimiento(), nacimiento);
        assertEquals(p.getSangre(), sangre);
        assertTrue(p.getDonaciones().size() == 0);
        pigual = new Persona("P", "l", "p", "d", "t", "m", true, nacimiento, sangre);
        pdiferente = new Persona("Pdiferente", "l", "p", "d", "t", "m", true, nacimiento, sangre);
        assertEquals(p, pigual);
        assertNotEquals(p, pdiferente);
        donaciones = p.getDonaciones();
        assertNotNull(donaciones);
        assertTrue(donaciones.isEmpty());
        d = new Donacion(p, new GregorianCalendar());
        p.agregarDonacion(d);
        donaciones = p.getDonaciones();
        assertTrue(donaciones.contains(d));
        assertEquals(donaciones.size(), 1);
        p.quitarDonacion(d);
        assertFalse(donaciones.contains(d));
        assertTrue(donaciones.isEmpty());
    }
}
