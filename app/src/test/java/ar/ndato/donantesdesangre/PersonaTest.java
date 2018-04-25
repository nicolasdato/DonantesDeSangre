package ar.ndato.donantesdesangre;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class PersonaTest {
    @Test
    public void personaTest() {
        Persona p, pigual, pdiferente;
        Date nacimiento = new Date();
        Sangre sangre = new ARhPFactory().crearSangre();
        Set<Donacion> donaciones;
        Donacion d;

        p = new Persona("P", "l", "p", "d", "t", "m", true, nacimiento, sangre, null);
        assertEquals(p.getNombre(), "P");
        assertEquals(p.getLocalidad(), "l");
        assertEquals(p.getProvincia(), "p");
        assertEquals(p.getDireccion(), "d");
        assertEquals(p.getTelefono(), "t");
        assertEquals(p.getMail(), "m");
        assertTrue(p.isFavorito());
        assertEquals(p.getNacimiento(), nacimiento);
        assertEquals(p.getSangre(), sangre);
        assertNull(p.getDonaciones());
        pigual = new Persona("P", "l", "p", "d", "t", "m", true, nacimiento, sangre, null);
        pdiferente = new Persona("Pdiferente", "l", "p", "d", "t", "m", true, nacimiento, sangre, null);
        assertEquals(p, pigual);
        assertNotEquals(p, pdiferente);
        donaciones = p.getDonaciones();
        assertNotEquals(donaciones);
        assertTrue(donaciones.isEmpty());
        d = new Donacion(p, new Date());
        p.agregarDonacion(d);
        donaciones = p.getDonaciones();
        assertTrue(donaciones.contains(d));
        assertEquals(donaciones.size(), 1);
        p.quitarDonacion(d);
        assertFalse(donaciones.contains(d));
        assertTrue(donaciones.isEmpty());
    }
}
